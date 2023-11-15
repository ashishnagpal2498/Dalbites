package com.asdc.dalbites.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.exception.AccountNotVerifiedException;
import com.asdc.dalbites.exception.DuplicateEntryException;
import com.asdc.dalbites.exception.OTPVerificationFailed;
import com.asdc.dalbites.model.DAO.LoginDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.model.DTO.ForgetPasswordDTO;
import com.asdc.dalbites.model.DTO.UserLoginDTO;
import com.asdc.dalbites.model.DTO.UserSignUpDTO;
import com.asdc.dalbites.model.DTO.VerifyAccountDTO;
import com.asdc.dalbites.repository.LoginRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.repository.RoleRepository;
import com.asdc.dalbites.repository.UserRepository;
import com.asdc.dalbites.service.LoginService;
import com.asdc.dalbites.util.JwtTokenUtil;
import com.asdc.dalbites.util.UtilityFunctions;

import io.jsonwebtoken.Claims;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class LoginServiceImpl implements LoginService {

    @Value("${role.user}")
    private int userRole;

    @Value("${role.restaurant}")
    private int restaurantRole;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
	private JwtTokenUtil jwtUtil;

    @Autowired
    private UtilityFunctions utilityFunctions;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginDao loginDao = loginRepository.findByUsername(username);
        if (loginDao == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
            .username(loginDao.getUsername())
            .password(loginDao.getPassword())
            .build();
    }

    @Override
    public HashMap<String, Object> create(UserSignUpDTO userSignUpDTO) throws Exception {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userSignUpDTO.setPassword(passwordEncoder.encode(userSignUpDTO.getPassword()));
            userSignUpDTO.setRoleId(roleRepository.findByName(userSignUpDTO.getRole()).getId());
            LoginDao loginDao = new LoginDao(userSignUpDTO.getUsername(), userSignUpDTO.getPassword());
            roleRepository.findById(userSignUpDTO.getRoleId()).map(roleDao -> { 
                loginDao.setRoleDao(roleDao);
                return true;
            });
            loginRepository.save(loginDao);
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("username", userSignUpDTO.getUsername());
            if (userSignUpDTO.getRoleId() == userRole) {
                UserDao userDao = new UserDao(userSignUpDTO.getName(), userSignUpDTO.getEmail());
                userDao.setLoginDao(loginDao);
                userRepository.save(userDao);
                claims.put("email", userDao.getEmail());
                claims.put("user_id", userDao.getUser_id());
            }
            else if (userSignUpDTO.getRoleId() == restaurantRole) {
                RestaurantDao restaurantDao = new RestaurantDao(userSignUpDTO.getName(), userSignUpDTO.getAddress());
                restaurantDao.setLoginDao(loginDao);
                restaurantRepository.save(restaurantDao);
                claims.put("restaurant_id", restaurantDao.getId());
                claims.put("email", loginDao.getUsername());
            }
            else {
                throw new Exception("Role not found in the system");
            }
            claims.put("login_id", loginDao.getId());
            claims.put("name", userSignUpDTO.getName());
            claims.put("role", userSignUpDTO.getRole());
            int otp = (int) Math.floor(100000 + Math.random() * 900000);
            claims.put("otp", otp);
            String jwtToken = jwtUtil.generateToken(claims);
            claims.put("token", jwtToken);
            return claims;
        } catch(DataIntegrityViolationException error) {
            throw new DuplicateEntryException("User with same username already exists", new Exception(error.getMessage()));
        }
    }

    @Override
    public Object getUserByUsername(String username) {
        LoginDao loginDao = loginRepository.findByUsername(username);
        if (loginDao.getRoleDao().getId() == userRole) {
            return userRepository.findByLogin_Id(loginDao.getId());
        }
        else if (loginDao.getRoleDao().getId() == restaurantRole) {
            return restaurantRepository.findByLogin_Id(loginDao.getId());
        }
        else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    @Override
    public Object login(UserLoginDTO userLoginDTO) throws Exception {
        LoginDao loginDao = loginRepository.findByUsername(userLoginDTO.getUsername());
        if (loginDao.getIs_verified() == 1) {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("username", loginDao.getUsername());
            claims.put("role_id", loginDao.getRoleDao().getId());
            claims.put("role", loginDao.getRoleDao().getName());
            if (loginDao.getRoleDao().getId() == userRole) {
                UserDao userDetails = (UserDao) getUserByUsername(userLoginDTO.getUsername());
                claims.put("name", userDetails.getName());
                claims.put("email", userDetails.getEmail());
            }
            else if (loginDao.getRoleDao().getId() == restaurantRole) {
                RestaurantDao restaurantDao = (RestaurantDao) getUserByUsername(userLoginDTO.getUsername());
                claims.put("name", restaurantDao.getName());
                claims.put("address", restaurantDao.getAddress());
            }
            String jwtToken = jwtUtil.generateToken(claims);
            claims.put("token", jwtToken);
            return claims;
        }
        else {
            throw new AccountNotVerifiedException("Account is not verified", new Exception("Account is not verified"));
        }
    }

    @Override
    public Object verifyAccount(String token, VerifyAccountDTO verifyAccountDTO) throws Exception {
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(7));
        String otp = tokenClaims.get("otp").toString();
        if(otp.equalsIgnoreCase(verifyAccountDTO.getOtp())) {
            loginRepository.setIsVerified((String) tokenClaims.get("username"));
            HashMap<String, Object> claims = new HashMap<>();
            String jwtToken = jwtUtil.generateToken(tokenClaims);
            claims.put("message", "Account Successfully Verified");
            claims.put("token", jwtToken);
            return claims;
        }
        else {
            throw new OTPVerificationFailed("OTP does not match", new Exception("OTP does not match", null));
        }
    }

    @Override
    public HashMap<String, Object> forgetPasswordRequest(ForgetPasswordDTO forgetPasswordDTO) throws Exception {
        LoginDao loginDao = loginRepository.findByUsername(forgetPasswordDTO.getUsername());
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username", loginDao.getUsername());
        claims.put("role", loginDao.getRoleDao().getName());
        claims.put("role_id", loginDao.getRoleDao().getId());
        claims.put("otp", utilityFunctions.generateOTP());
        if (loginDao.getRoleDao().getId() == userRole) {
            UserDao userDetails = (UserDao) getUserByUsername(loginDao.getUsername());
            claims.put("name", userDetails.getName());
            claims.put("email", userDetails.getEmail());
        }
        else if (loginDao.getRoleDao().getId() == restaurantRole) {
            RestaurantDao restaurantDao = (RestaurantDao) getUserByUsername(loginDao.getUsername());
            claims.put("name", restaurantDao.getName());
            claims.put("address", restaurantDao.getAddress());
            claims.put("email", loginDao.getUsername());
        }
        String jwtToken = jwtUtil.generateFifteenMinuteExpiryToken(claims);
        claims.put("token", jwtToken);
        return claims;
    }

    @Override
    public HashMap<String, Object> forgetPasswordVerification(String token, ForgetPasswordDTO forgetPasswordDTO)  throws Exception {
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(7));
        String otp = tokenClaims.get("otp").toString();
        if(otp.equalsIgnoreCase(forgetPasswordDTO.getOtp())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            loginRepository.setPassword((String) tokenClaims.get("username"), passwordEncoder.encode(forgetPasswordDTO.getPassword()));
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("message", "Password changed successfully");
            return claims;
        }
        else {
            throw new OTPVerificationFailed("OTP does not match", new Exception("OTP does not match", null));
        }
    }
}

package com.asdc.dalbites.service.impl;

import org.springframework.security.core.userdetails.User;
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
import com.asdc.dalbites.util.Constants;

import io.jsonwebtoken.Claims;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;


/**
 * Implementation of the {@link LoginService} interface providing methods for user authentication,
 * account management, and password reset.
 */
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

    /**
     * Loads user details by username for authentication purposes.
     *
     * @param username The username of the user.
     * @return UserDetails containing user information.
     * @throws UsernameNotFoundException If the username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginDao loginDao = loginRepository.findByUsername(username);
        if (loginDao == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        return User.builder().username(loginDao.getUsername())
            .password(loginDao.getPassword())
            .build();
    }

    /**
     * Creates a new user or restaurant account.
     *
     * @param userSignUpDTO The data transfer object containing user or restaurant information for sign-up.
     * @return A {@code HashMap} containing information about the created account, including a JWT token.
     * @throws Exception If there is a failure in creating the account.
     */
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
                claims.put("user_id", userDao.getUserId());
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
            int otp = (int) Math.floor(Constants.RANDOM_NUMBER_START + Math.random() * Constants.RANDOM_NUMBER_END);
            claims.put("otp", otp);
            String jwtToken = jwtUtil.generateToken(claims);
            claims.put("token", jwtToken);
            return claims;
        } catch(DataIntegrityViolationException error) {
            throw new DuplicateEntryException("User with same username already exists", new Exception(error.getMessage()));
        }
    }

    /**
     * Retrieves user or restaurant details by username.
     *
     * @param username The username of the user or restaurant.
     * @return User or restaurant details.
     * @throws UsernameNotFoundException If the user or restaurant is not found.
     */
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

    /**
     * Authenticates a user and generates a JWT token upon successful login.
     *
     * @param userLoginDTO The data transfer object containing user login information.
     * @return A {@code HashMap} containing information about the user, including a JWT token.
     * @throws Exception If the account is not verified or other authentication issues occur.
     */
    @Override
    public Object login(UserLoginDTO userLoginDTO) throws Exception {
        LoginDao loginDao = loginRepository.findByUsername(userLoginDTO.getUsername());
        if (loginDao.getIs_verified() == 1) {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("username", loginDao.getUsername());
            claims.put("role_id", loginDao.getRoleDao().getId());
            claims.put("role", loginDao.getRoleDao().getName());
            claims.put("login_id",loginDao.getId());
            if (loginDao.getRoleDao().getId() == userRole) {
                UserDao userDetails = (UserDao) getUserByUsername(userLoginDTO.getUsername());
                claims.put("name", userDetails.getName());
                claims.put("email", userDetails.getEmail());
                claims.put("user_id", userDetails.getUserId());
            }
            else if (loginDao.getRoleDao().getId() == restaurantRole) {
                RestaurantDao restaurantDao = (RestaurantDao) getUserByUsername(userLoginDTO.getUsername());
                claims.put("name", restaurantDao.getName());
                claims.put("address", restaurantDao.getAddress());
                claims.put("restaurant_id", restaurantDao.getId());
            }
            String jwtToken = jwtUtil.generateToken(claims);
            claims.put("token", jwtToken);
            return claims;
        }
        else {
            throw new AccountNotVerifiedException("Account is not verified", new Exception("Account is not verified"));
        }
    }

    /**
     * Verifies a user's account using the provided token and OTP.
     *
     * @param token            The JWT token containing user information.
     * @param verifyAccountDTO The data transfer object containing the OTP for verification.
     * @return A {@code HashMap} with a success message and a new JWT token.
     * @throws Exception If OTP verification fails or other issues occur during the verification process.
     */
    @Override
    public Object verifyAccount(String token, VerifyAccountDTO verifyAccountDTO) throws Exception {
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(Constants.TOKEN_START_INDEX));
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

    /**
     * Initiates a request for forgetting the user's password and sends an OTP for verification.
     *
     * @param forgetPasswordDTO The data transfer object containing the username for password reset.
     * @return A {@code HashMap} containing a success message and a JWT token for OTP verification.
     * @throws Exception If there are issues during the password reset request.
     */
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

    /**
     * Verifies the user's identity using the provided token and OTP and resets the password.
     *
     * @param token              The JWT token containing user information.
     * @param forgetPasswordDTO The data transfer object containing the OTP and new password.
     * @return A {@code HashMap} with a success message.
     * @throws Exception If OTP verification fails or other issues occur during password reset.
     */
    @Override
    public HashMap<String, Object> forgetPasswordVerification(String token, ForgetPasswordDTO forgetPasswordDTO)  throws Exception {
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(Constants.TOKEN_START_INDEX));
        String otp = tokenClaims.get("otp").toString();
        if(otp.equalsIgnoreCase(forgetPasswordDTO.getOtp())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String username = (String) tokenClaims.get("username");
            String password = forgetPasswordDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            loginRepository.setPassword(username, encodedPassword);
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("message", "Password changed successfully");
            return claims;
        }
        else {
            throw new OTPVerificationFailed("OTP does not match", new Exception("OTP does not match", null));
        }
    }
}

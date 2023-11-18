package com.asdc.dalbites.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import com.asdc.dalbites.exception.AccountNotVerifiedException;
import com.asdc.dalbites.exception.DuplicateEntryException;
import com.asdc.dalbites.exception.OTPVerificationFailed;
import com.asdc.dalbites.model.DAO.LoginDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DAO.RoleDao;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.model.DTO.UserLoginDTO;
import com.asdc.dalbites.model.DTO.UserSignUpDTO;
import com.asdc.dalbites.model.DTO.VerifyAccountDTO;
import com.asdc.dalbites.repository.LoginRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.repository.RoleRepository;
import com.asdc.dalbites.repository.UserRepository;
import com.asdc.dalbites.service.impl.LoginServiceImpl;
import com.asdc.dalbites.util.JwtTokenUtil;
import com.asdc.dalbites.util.UtilityFunctions;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtTokenUtil jwtUtil;

    @Mock
    private UtilityFunctions utilityFunctions;

    @InjectMocks
    private LoginServiceImpl loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock the values for userRole and restaurantRole using ReflectionTestUtils
        ReflectionTestUtils.setField(loginService, "userRole", 1);
        ReflectionTestUtils.setField(loginService, "restaurantRole", 2);
    }
    
    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        LoginDao mockLoginDao = new LoginDao("testuser", "password");
        when(loginRepository.findByUsername("testuser")).thenReturn(mockLoginDao);

        assertDoesNotThrow(() -> loginService.loadUserByUsername("testuser"));
    }

    @Test
    void loadUserByUsername_UserNotExists_ThrowsUsernameNotFoundException() {
        when(loginRepository.findByUsername("nonexistentuser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> loginService.loadUserByUsername("nonexistentuser"));
    }

    @Test
    void testCreate_DuplicateEntryException() throws Exception {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setUsername("existingUser");
        userSignUpDTO.setPassword("testPassword");
        userSignUpDTO.setRole("USER");

        RoleDao roleDao = new RoleDao();
        roleDao.setId(1);
        roleDao.setName("USER");

        when(roleRepository.findByName(userSignUpDTO.getRole())).thenReturn(roleDao);
        when(loginRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateEntryException.class, () -> loginService.create(userSignUpDTO));
    }

    @Test
    void testGetUserByUsername_InvalidRole() {
        String username = "admin1";
        LoginDao loginDao = new LoginDao(username, "password");
        loginDao.setRoleDao(new RoleDao("ADMIN"));
        when(loginRepository.findByUsername(username)).thenReturn(loginDao);

        assertThrows(UsernameNotFoundException.class, () -> loginService.getUserByUsername(username));
    }
    
    @Test
    void testGetUserByUsername_UserRole() {
        String username = "user1";
        LoginDao loginDao = new LoginDao(username, "password");
        RoleDao roleDao = new RoleDao("user");
        roleDao.setId(1);
        loginDao.setRoleDao(roleDao);
        UserDao userDao = new UserDao("John Doe", "john@example.com");
        userDao.setLoginDao(loginDao);
        when(loginRepository.findByUsername(username)).thenReturn(loginDao);
        when(userRepository.findByLogin_Id(loginDao.getId())).thenReturn(userDao);

        Object result = loginService.getUserByUsername(username);

        assertNotNull(result);
        assertTrue(result instanceof UserDao);
        assertEquals(userDao, result);
    }
    
    @Test
    void testGetUserByUsername_RestaurantRole() {
        String username = "restaurant1";
        LoginDao loginDao = new LoginDao(username, "password");
        RoleDao roleDao = new RoleDao("restaurant");
        roleDao.setId(2);
        loginDao.setRoleDao(roleDao);
        RestaurantDao restaurantDao = new RestaurantDao("Restaurant One", "123 Main St");
        restaurantDao.setLoginDao(loginDao);

        when(loginRepository.findByUsername(username)).thenReturn(loginDao);
        when(restaurantRepository.findByLogin_Id(loginDao.getId())).thenReturn(restaurantDao);

        Object result = loginService.getUserByUsername(username);

        assertEquals(restaurantDao.getLoginDao().getId(), ((RestaurantDao) result).getLoginDao().getId());
        assertEquals(restaurantDao.getName(), ((RestaurantDao) result).getName());
        assertEquals(restaurantDao.getAddress(), ((RestaurantDao) result).getAddress());
    }
    
    @Test
    void testVerifyAccount_InvalidOTP() {
        String token = "validToken";
        VerifyAccountDTO verifyAccountDTO = new VerifyAccountDTO();
        verifyAccountDTO.setOtp("invalidOTP");

        Claims tokenClaims = mock(Claims.class);
        when(jwtUtil.getAllClaimsFromToken(token.substring(7))).thenReturn(tokenClaims);
        when(tokenClaims.get("otp")).thenReturn("123456");

        assertThrows(OTPVerificationFailed.class, () -> loginService.verifyAccount(token, verifyAccountDTO));
    }
    
    @Test
    void testVerifyAccount() throws Exception {
        String token = "validToken";
        VerifyAccountDTO verifyAccountDTO = new VerifyAccountDTO();
        verifyAccountDTO.setOtp("123456");

        Claims tokenClaims = mock(Claims.class);
        when(jwtUtil.getAllClaimsFromToken(token.substring(7))).thenReturn(tokenClaims);
        when(tokenClaims.get("otp")).thenReturn("123456");

        assertDoesNotThrow(() -> loginService.verifyAccount(token, verifyAccountDTO));
    }
    @Test
    void testLogin_AccountNotVerifiedException() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("unverifiedUser");

        LoginDao loginDao = new LoginDao(userLoginDTO.getUsername(), "testPassword");
        loginDao.setIs_verified(0);

        when(loginRepository.findByUsername(userLoginDTO.getUsername())).thenReturn(loginDao);

        assertThrows(AccountNotVerifiedException.class, () -> loginService.login(userLoginDTO));
    }
    
    @Test
    void testCreate_InvalidRole() {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setRole("INVALID_ROLE");

        assertThrows(Exception.class, () -> loginService.create(userSignUpDTO));
    }
    
    @Test
    void testCreate_RestaurantRole() {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setUsername("restaurantUser");
        userSignUpDTO.setPassword("testPassword");
        userSignUpDTO.setRole("RESTAURANT");

        RoleDao roleDao = new RoleDao();
        roleDao.setId(2);
        roleDao.setName("RESTAURANT");

        when(roleRepository.findByName(userSignUpDTO.getRole())).thenReturn(roleDao);
        when(loginRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () -> loginService.create(userSignUpDTO));
        assertTrue(exception.getMessage().contains("User with same username already exists"));
    }
}


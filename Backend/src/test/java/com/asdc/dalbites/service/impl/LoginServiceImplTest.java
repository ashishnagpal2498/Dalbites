package com.asdc.dalbites.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.asdc.dalbites.model.DTO.ForgetPasswordDTO;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
    void testCreate() throws Exception {
        when(loginRepository.save((LoginDao) any())).thenReturn(new LoginDao("demousername", "demopwd"));
        when(roleRepository.findById((Integer) any())).thenReturn(Optional.of(new RoleDao("Name")));
        when(roleRepository.findByName((String) any())).thenReturn(new RoleDao("Name"));

        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setAddress("42 Main St");
        userSignUpDTO.setEmail("jane.doe@example.org");
        userSignUpDTO.setName("Name");
        userSignUpDTO.setPassword("demopwd");
        userSignUpDTO.setRole("Role");
        userSignUpDTO.setRoleId(1);
        userSignUpDTO.setUsername("janedoe");
        assertThrows(Exception.class, () -> loginService.create(userSignUpDTO));
        verify(loginRepository).save((LoginDao) any());
        verify(roleRepository).findByName((String) any());
        verify(roleRepository).findById((Integer) any());
    }

    @Test
    void testCreate2() throws Exception {
        when(loginRepository.save((LoginDao) any())).thenReturn(new LoginDao("demouser", "demopwd"));
        when(roleRepository.findById((Integer) any())).thenThrow(new UsernameNotFoundException("username"));
        when(roleRepository.findByName((String) any())).thenReturn(new RoleDao("Name"));

        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setAddress("42 Main St");
        userSignUpDTO.setEmail("jane.doe@example.org");
        userSignUpDTO.setName("Name");
        userSignUpDTO.setPassword("demopwd");
        userSignUpDTO.setRole("Role");
        userSignUpDTO.setRoleId(1);
        userSignUpDTO.setUsername("janedoe");
        assertThrows(UsernameNotFoundException.class, () -> loginService.create(userSignUpDTO));
        verify(roleRepository).findByName((String) any());
        verify(roleRepository).findById((Integer) any());
    }

    @Test
    void testCreate3() throws Exception {
        when(loginRepository.save((LoginDao) any())).thenReturn(new LoginDao("janedoe", "demopwd"));
        when(roleRepository.findById((Integer) any())).thenThrow(new DataIntegrityViolationException("username"));
        when(roleRepository.findByName((String) any())).thenReturn(new RoleDao("Name"));

        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setAddress("42 Main St");
        userSignUpDTO.setEmail("jane.doe@example.org");
        userSignUpDTO.setName("Name");
        userSignUpDTO.setPassword("demopwd");
        userSignUpDTO.setRole("Role");
        userSignUpDTO.setRoleId(1);
        userSignUpDTO.setUsername("janedoe");
        assertThrows(DuplicateEntryException.class, () -> loginService.create(userSignUpDTO));
        verify(roleRepository).findByName((String) any());
        verify(roleRepository).findById((Integer) any());
    }

    @Test
    void testCreate4() throws Exception {
        when(loginRepository.save((LoginDao) any())).thenReturn(new LoginDao("janedoe", "demopwd"));
        when(roleRepository.findById((Integer) any())).thenReturn(Optional.empty());
        when(roleRepository.findByName((String) any())).thenReturn(new RoleDao("Name"));

        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setAddress("42 Main St");
        userSignUpDTO.setEmail("jane.doe@example.org");
        userSignUpDTO.setName("Name");
        userSignUpDTO.setPassword("demopwd");
        userSignUpDTO.setRole("Role");
        userSignUpDTO.setRoleId(1);
        userSignUpDTO.setUsername("janedoe");
        assertThrows(Exception.class, () -> loginService.create(userSignUpDTO));
        verify(loginRepository).save((LoginDao) any());
        verify(roleRepository).findByName((String) any());
        verify(roleRepository).findById((Integer) any());
    }

    @Test
    void testCreate5() throws Exception {
        when(jwtUtil.generateToken((Map<String, Object>) any())).thenReturn("ABC123");
        when(loginRepository.save((LoginDao) any())).thenReturn(new LoginDao("janedoe", "demopwd"));
        RoleDao roleDao = mock(RoleDao.class);
        when(roleDao.getId()).thenReturn(1);
        when(roleRepository.findById((Integer) any())).thenReturn(Optional.of(new RoleDao("Name")));
        when(roleRepository.findByName((String) any())).thenReturn(roleDao);
        when(userRepository.save((UserDao) any())).thenReturn(new UserDao("Name", "jane.doe@example.org"));

        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setAddress("42 Main St");
        userSignUpDTO.setEmail("jane.doe@example.org");
        userSignUpDTO.setName("Name");
        userSignUpDTO.setPassword("demopwd");
        userSignUpDTO.setRole("Role");
        userSignUpDTO.setRoleId(1);
        userSignUpDTO.setUsername("janedoe");
        assertEquals(8, loginService.create(userSignUpDTO).size());
        verify(jwtUtil).generateToken((Map<String, Object>) any());
        verify(loginRepository).save((LoginDao) any());
        verify(roleRepository).findByName((String) any());
        verify(roleRepository).findById((Integer) any());
        verify(roleDao).getId();
        verify(userRepository).save((UserDao) any());
        assertEquals(1, userSignUpDTO.getRoleId());
    }

    @Test
    void testCreate6() throws Exception {
        when(jwtUtil.generateToken((Map<String, Object>) any())).thenReturn("ABC123");
        when(loginRepository.save((LoginDao) any())).thenReturn(new LoginDao("janedoe", "demopwd"));
        RoleDao roleDao = mock(RoleDao.class);
        when(roleDao.getId()).thenReturn(1);
        when(roleRepository.findById((Integer) any())).thenReturn(Optional.of(new RoleDao("Name")));
        when(roleRepository.findByName((String) any())).thenReturn(roleDao);
        when(userRepository.save((UserDao) any())).thenThrow(new UsernameNotFoundException("username"));

        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setAddress("42 Main St");
        userSignUpDTO.setEmail("jane.doe@example.org");
        userSignUpDTO.setName("Name");
        userSignUpDTO.setPassword("demopwd");
        userSignUpDTO.setRole("Role");
        userSignUpDTO.setRoleId(1);
        userSignUpDTO.setUsername("janedoe");
        assertThrows(UsernameNotFoundException.class, () -> loginService.create(userSignUpDTO));
        verify(loginRepository).save((LoginDao) any());
        verify(roleRepository).findByName((String) any());
        verify(roleRepository).findById((Integer) any());
        verify(roleDao).getId();
        verify(userRepository).save((UserDao) any());
    }

    @Test
    void testCreate7() throws Exception {
        when(jwtUtil.generateToken((Map<String, Object>) any())).thenReturn("ABC123");
        when(loginRepository.save((LoginDao) any())).thenReturn(new LoginDao("janedoe", "demopwd"));
        RoleDao roleDao = mock(RoleDao.class);
        when(roleDao.getId()).thenReturn(1);
        when(roleRepository.findById((Integer) any())).thenReturn(Optional.of(new RoleDao("Name")));
        when(roleRepository.findByName((String) any())).thenReturn(roleDao);
        when(userRepository.save((UserDao) any())).thenThrow(new DataIntegrityViolationException("username"));

        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setAddress("42 Main St");
        userSignUpDTO.setEmail("jane.doe@example.org");
        userSignUpDTO.setName("Name");
        userSignUpDTO.setPassword("demopwd");
        userSignUpDTO.setRole("Role");
        userSignUpDTO.setRoleId(1);
        userSignUpDTO.setUsername("janedoe");
        assertThrows(DuplicateEntryException.class, () -> loginService.create(userSignUpDTO));
        verify(loginRepository).save((LoginDao) any());
        verify(roleRepository).findByName((String) any());
        verify(roleRepository).findById((Integer) any());
        verify(roleDao).getId();
        verify(userRepository).save((UserDao) any());
    }

    @Test
    void testCreate8() throws Exception {
        when(jwtUtil.generateToken((Map<String, Object>) any())).thenReturn("username");
        when(loginRepository.save((LoginDao) any())).thenReturn(new LoginDao("janedoe", "demopwd"));
        RoleDao roleDao = mock(RoleDao.class);
        when(roleDao.getId()).thenThrow(new DataIntegrityViolationException("Msg"));
        when(roleRepository.findById((Integer) any())).thenReturn(Optional.of(new RoleDao("Name")));
        when(roleRepository.findByName((String) any())).thenReturn(roleDao);
        when(userRepository.save((UserDao) any())).thenReturn(new UserDao("Name", "jane.doe@example.org"));

        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setAddress("42 Main St");
        userSignUpDTO.setEmail("jane.doe@example.org");
        userSignUpDTO.setName("Name");
        userSignUpDTO.setPassword("demopwd");
        userSignUpDTO.setRole("Role");
        userSignUpDTO.setRoleId(1);
        userSignUpDTO.setUsername("janedoe");
        assertThrows(DuplicateEntryException.class, () -> loginService.create(userSignUpDTO));
        verify(roleRepository).findByName((String) any());
        verify(roleDao).getId();
    }

    @Test
    void testCreate9() throws Exception {
        when(jwtUtil.generateToken((Map<String, Object>) any())).thenReturn("username");
        when(loginRepository.save((LoginDao) any())).thenReturn(new LoginDao("janedoe", "demopwd"));
        when(roleRepository.findById((Integer) any())).thenReturn(Optional.of(new RoleDao("Name")));
        when(roleRepository.findByName((String) any())).thenReturn(mock(RoleDao.class));
        new DataIntegrityViolationException("Msg");
        when(userRepository.save((UserDao) any())).thenReturn(new UserDao("Name", "jane.doe@example.org"));
        UserSignUpDTO userSignUpDTO = mock(UserSignUpDTO.class);
        when(userSignUpDTO.getRoleId()).thenThrow(new DataIntegrityViolationException("Msg"));
        when(userSignUpDTO.getRole()).thenThrow(new DataIntegrityViolationException("Msg"));
        when(userSignUpDTO.getUsername()).thenThrow(new DataIntegrityViolationException("Msg"));
        when(userSignUpDTO.getPassword()).thenReturn("demopwd");
        doNothing().when(userSignUpDTO).setAddress((String) any());
        doNothing().when(userSignUpDTO).setEmail((String) any());
        doNothing().when(userSignUpDTO).setName((String) any());
        doNothing().when(userSignUpDTO).setPassword((String) any());
        doNothing().when(userSignUpDTO).setRole((String) any());
        doNothing().when(userSignUpDTO).setRoleId(anyInt());
        doNothing().when(userSignUpDTO).setUsername((String) any());
        userSignUpDTO.setAddress("42 Main St");
        userSignUpDTO.setEmail("jane.doe@example.org");
        userSignUpDTO.setName("Name");
        userSignUpDTO.setPassword("demopwd");
        userSignUpDTO.setRole("Role");
        userSignUpDTO.setRoleId(1);
        userSignUpDTO.setUsername("janedoe");
        assertThrows(DuplicateEntryException.class, () -> loginService.create(userSignUpDTO));
        verify(userSignUpDTO).getPassword();
        verify(userSignUpDTO).getRole();
        verify(userSignUpDTO).setAddress((String) any());
        verify(userSignUpDTO).setEmail((String) any());
        verify(userSignUpDTO).setName((String) any());
        verify(userSignUpDTO, atLeast(1)).setPassword((String) any());
        verify(userSignUpDTO).setRole((String) any());
        verify(userSignUpDTO).setRoleId(anyInt());
        verify(userSignUpDTO).setUsername((String) any());
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
    void testForgetPasswordRequest() throws Exception {
        when(jwtUtil.generateFifteenMinuteExpiryToken((Map<String, Object>) any())).thenReturn("ABC123");

        LoginDao loginDao = new LoginDao("janedoe", "abcd");
        loginDao.setRoleDao(new RoleDao("username"));
        when(loginRepository.findByUsername((String) any())).thenReturn(loginDao);
        when(utilityFunctions.generateOTP()).thenReturn(1);

        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        forgetPasswordDTO.setOtp("Otp");
        forgetPasswordDTO.setPassword("abcd");
        forgetPasswordDTO.setUsername("janedoe");
        assertEquals(5, loginService.forgetPasswordRequest(forgetPasswordDTO).size());
        verify(jwtUtil).generateFifteenMinuteExpiryToken((Map<String, Object>) any());
        verify(loginRepository).findByUsername((String) any());
        verify(utilityFunctions).generateOTP();
    }

    @Test
    void testForgetPasswordRequest2() throws Exception {
        when(jwtUtil.generateFifteenMinuteExpiryToken((Map<String, Object>) any())).thenReturn("ABC123");
        RoleDao roleDao = mock(RoleDao.class);
        when(roleDao.getId()).thenReturn(1);
        when(roleDao.getName()).thenReturn("Name");

        LoginDao loginDao = new LoginDao("janedoe", "abcd");
        loginDao.setRoleDao(roleDao);
        when(loginRepository.findByUsername((String) any())).thenReturn(loginDao);
        when(userRepository.findByLogin_Id((Long) any())).thenReturn(new UserDao("Name", "jane.doe@example.org"));
        when(utilityFunctions.generateOTP()).thenReturn(1);

        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        forgetPasswordDTO.setOtp("Otp");
        forgetPasswordDTO.setPassword("abcd");
        forgetPasswordDTO.setUsername("janedoe");
        assertEquals(7, loginService.forgetPasswordRequest(forgetPasswordDTO).size());
        verify(jwtUtil).generateFifteenMinuteExpiryToken((Map<String, Object>) any());
        verify(loginRepository, atLeast(1)).findByUsername((String) any());
        verify(roleDao, atLeast(1)).getId();
        verify(roleDao).getName();
        verify(userRepository).findByLogin_Id((Long) any());
        verify(utilityFunctions).generateOTP();
    }

    @Test
    void testForgetPasswordRequest3() throws Exception {
        when(jwtUtil.generateFifteenMinuteExpiryToken((Map<String, Object>) any())).thenReturn("ABC123");
        RoleDao roleDao = mock(RoleDao.class);
        when(roleDao.getId()).thenReturn(1);
        when(roleDao.getName()).thenReturn("Name");

        LoginDao loginDao = new LoginDao("janedoe", "abcd");
        loginDao.setRoleDao(roleDao);
        when(loginRepository.findByUsername((String) any())).thenReturn(loginDao);
        when(userRepository.findByLogin_Id((Long) any())).thenThrow(new UsernameNotFoundException("username"));
        when(utilityFunctions.generateOTP()).thenReturn(1);

        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        forgetPasswordDTO.setOtp("Otp");
        forgetPasswordDTO.setPassword("abcd");
        forgetPasswordDTO.setUsername("janedoe");
        assertThrows(UsernameNotFoundException.class, () -> loginService.forgetPasswordRequest(forgetPasswordDTO));
        verify(loginRepository, atLeast(1)).findByUsername((String) any());
        verify(roleDao, atLeast(1)).getId();
        verify(roleDao).getName();
        verify(userRepository).findByLogin_Id((Long) any());
        verify(utilityFunctions).generateOTP();
    }

    @Test
    void testForgetPasswordVerification() throws Exception {
        DefaultClaims defaultClaims = new DefaultClaims();
        defaultClaims.put("otp", "42");
        when(jwtUtil.getAllClaimsFromToken((String) any())).thenReturn(defaultClaims);

        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        forgetPasswordDTO.setOtp("Otp");
        forgetPasswordDTO.setPassword("abcd");
        forgetPasswordDTO.setUsername("janedoe");
        assertThrows(OTPVerificationFailed.class,
                () -> loginService.forgetPasswordVerification("java.lang.String", forgetPasswordDTO));
        verify(jwtUtil).getAllClaimsFromToken((String) any());
    }

    @Test
    void testForgetPasswordVerification2() throws Exception {
        DefaultClaims defaultClaims = new DefaultClaims();
        defaultClaims.put("otp", "42");
        when(jwtUtil.getAllClaimsFromToken((String) any())).thenReturn(defaultClaims);
        when(loginRepository.setPassword((String) any(), (String) any())).thenReturn(1);

        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        forgetPasswordDTO.setOtp("42");
        forgetPasswordDTO.setPassword("abcd");
        forgetPasswordDTO.setUsername("janedoe");
        assertEquals(1, loginService.forgetPasswordVerification("java.lang.String", forgetPasswordDTO).size());
        verify(jwtUtil).getAllClaimsFromToken((String) any());
        verify(loginRepository).setPassword((String) any(), (String) any());
    }

    @Test
    void testForgetPasswordVerification3() throws Exception {
        DefaultClaims defaultClaims = new DefaultClaims();
        defaultClaims.put("otp", "42");
        when(jwtUtil.getAllClaimsFromToken((String) any())).thenReturn(defaultClaims);
        when(loginRepository.setPassword((String) any(), (String) any())).thenThrow(new UsernameNotFoundException("otp"));

        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        forgetPasswordDTO.setOtp("42");
        forgetPasswordDTO.setPassword("abcd");
        forgetPasswordDTO.setUsername("janedoe");
        assertThrows(UsernameNotFoundException.class,
                () -> loginService.forgetPasswordVerification("java.lang.String", forgetPasswordDTO));
        verify(jwtUtil).getAllClaimsFromToken((String) any());
        verify(loginRepository).setPassword((String) any(), (String) any());
    }

    @Test
    void testLogin() throws Exception {
        when(loginRepository.findByUsername((String) any())).thenReturn(new LoginDao("janedoe", "abcd"));

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setPassword("abcd");
        userLoginDTO.setUsername("janedoe");
        assertThrows(AccountNotVerifiedException.class, () -> loginService.login(userLoginDTO));
        verify(loginRepository).findByUsername((String) any());
    }

    @Test
    void testLogin2() throws Exception {
        when(jwtUtil.generateToken((Map<String, Object>) any())).thenReturn("ABC123");
        LoginDao loginDao = mock(LoginDao.class);
        when(loginDao.getId()).thenReturn(1L);
        when(loginDao.getRoleDao()).thenReturn(new RoleDao("Name"));
        when(loginDao.getUsername()).thenReturn("janedoe");
        when(loginDao.getIs_verified()).thenReturn(1);
        when(loginRepository.findByUsername((String) any())).thenReturn(loginDao);

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setPassword("abcd");
        userLoginDTO.setUsername("janedoe");
        assertEquals(5, ((Map<String, Object>) loginService.login(userLoginDTO)).size());
        verify(jwtUtil).generateToken((Map<String, Object>) any());
        verify(loginRepository).findByUsername((String) any());
        verify(loginDao, atLeast(1)).getRoleDao();
        verify(loginDao).getIs_verified();
        verify(loginDao).getId();
        verify(loginDao).getUsername();
    }

    @Test
    void testLogin3() throws Exception {
        when(jwtUtil.generateToken((Map<String, Object>) any())).thenReturn("username");

        RoleDao roleDao = new RoleDao("Name");
        roleDao.setId(1);
        LoginDao loginDao = mock(LoginDao.class);
        when(loginDao.getId()).thenReturn(1L);
        when(loginDao.getRoleDao()).thenReturn(roleDao);
        when(loginDao.getUsername()).thenReturn("janedoe");
        when(loginDao.getIs_verified()).thenReturn(1);
        when(loginRepository.findByUsername((String) any())).thenReturn(loginDao);
        new DataIntegrityViolationException("username");
        new DataIntegrityViolationException("username");
        when(userRepository.findByLogin_Id((Long) any())).thenReturn(new UserDao("Name", "jane.doe@example.org"));

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setPassword("abcd");
        userLoginDTO.setUsername("janedoe");
        assertEquals(8, ((Map<String, Object>) loginService.login(userLoginDTO)).size());
        verify(jwtUtil).generateToken((Map<String, Object>) any());
        verify(loginRepository, atLeast(1)).findByUsername((String) any());
        verify(loginDao, atLeast(1)).getRoleDao();
        verify(loginDao).getIs_verified();
        verify(loginDao, atLeast(1)).getId();
        verify(loginDao).getUsername();
        verify(userRepository).findByLogin_Id((Long) any());
    }

    @Test
    void testLogin4() throws Exception {
        when(jwtUtil.generateToken((Map<String, Object>) any())).thenReturn("username");

        RoleDao roleDao = new RoleDao("Name");
        roleDao.setId(1);
        LoginDao loginDao = mock(LoginDao.class);
        when(loginDao.getId()).thenReturn(1L);
        when(loginDao.getRoleDao()).thenReturn(roleDao);
        when(loginDao.getUsername()).thenReturn("janedoe");
        when(loginDao.getIs_verified()).thenReturn(1);
        when(loginRepository.findByUsername((String) any())).thenReturn(loginDao);
        new DataIntegrityViolationException("username");
        new DataIntegrityViolationException("username");
        when(userRepository.findByLogin_Id((Long) any())).thenThrow(new UsernameNotFoundException("username"));

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setPassword("abcd");
        userLoginDTO.setUsername("janedoe");
        assertThrows(UsernameNotFoundException.class, () -> loginService.login(userLoginDTO));
        verify(loginRepository, atLeast(1)).findByUsername((String) any());
        verify(loginDao, atLeast(1)).getRoleDao();
        verify(loginDao).getIs_verified();
        verify(loginDao, atLeast(1)).getId();
        verify(loginDao).getUsername();
        verify(userRepository).findByLogin_Id((Long) any());
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


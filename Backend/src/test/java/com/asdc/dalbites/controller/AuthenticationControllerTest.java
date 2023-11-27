package com.asdc.dalbites.controller;

import com.asdc.dalbites.exception.AccountNotVerifiedException;
import com.asdc.dalbites.exception.DuplicateEntryException;
import com.asdc.dalbites.exception.InvalidCredentialException;
import com.asdc.dalbites.model.DTO.ForgetPasswordDTO;
import com.asdc.dalbites.model.DTO.UserLoginDTO;
import com.asdc.dalbites.model.DTO.UserSignUpDTO;
import com.asdc.dalbites.model.DTO.VerifyAccountDTO;
import com.asdc.dalbites.service.LoginService;
import com.asdc.dalbites.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private LoginService loginService;

    @Mock
    private EmailServiceImpl emailService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void doSignup_Success() throws Exception {
        UserSignUpDTO userSignUpDTO = createUserSignUpDTO();
        when(loginService.create(userSignUpDTO)).thenReturn(createClaims());
        ResponseEntity<?> response = authenticationController.doSignup(userSignUpDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
        verify(loginService, times(1)).create(userSignUpDTO);
    }

    @Test
    void doSignup_InternalServerError() throws Exception {
        UserSignUpDTO userSignUpDTO = createUserSignUpDTO();
        when(loginService.create(userSignUpDTO)).thenThrow(new Exception("Internal Server Error"));
        ResponseEntity<?> response = authenticationController.doSignup(userSignUpDTO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(loginService, times(1)).create(userSignUpDTO);
    }

    @Test
    void doLogin_Success() throws Exception {
        UserLoginDTO userLoginDTO = createUserLoginDTO();
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(loginService.login(userLoginDTO)).thenReturn(createClaims());
        ResponseEntity<?> response = authenticationController.doLogin(userLoginDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(loginService, times(1)).login(userLoginDTO);
    }

    @Test
    void doLogin_InternalServerError() throws Exception {
        UserLoginDTO userLoginDTO = createUserLoginDTO();
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(loginService.login(userLoginDTO)).thenThrow(new Exception("Internal Server Error"));
        ResponseEntity<?> response = authenticationController.doLogin(userLoginDTO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(loginService, times(1)).login(userLoginDTO);
    }

    private UserSignUpDTO createUserSignUpDTO() {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setUsername("testUser");
        userSignUpDTO.setPassword("testPassword");
        // Set other properties as needed for testing
        return userSignUpDTO;
    }

    private UserLoginDTO createUserLoginDTO() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("testUser");
        userLoginDTO.setPassword("testPassword");
        // Set other properties as needed for testing
        return userLoginDTO;
    }

    private HashMap<String, Object> createClaims() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", "test@example.com");
        claims.put("otp", "123456");
        // Add other claims as needed for testing
        return claims;
    }


    @Test
    void verifyAccount_Success() throws Exception {
        // Arrange
        String bearerToken = "BearerToken";
        VerifyAccountDTO verifyAccountDTO = createVerifyAccountDTO();
        when(loginService.verifyAccount(bearerToken, verifyAccountDTO)).thenReturn(createClaims());

        // Act
        ResponseEntity<?> response = authenticationController.verifyAccount(bearerToken, verifyAccountDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(loginService, times(1)).verifyAccount(bearerToken, verifyAccountDTO);
    }

    @Test
    void verifyAccount_InternalServerError() throws Exception {
        // Arrange
        String bearerToken = "BearerToken";
        VerifyAccountDTO verifyAccountDTO = createVerifyAccountDTO();
        when(loginService.verifyAccount(bearerToken, verifyAccountDTO)).thenThrow(new Exception("Internal Server Error"));

        // Act
        ResponseEntity<?> response = authenticationController.verifyAccount(bearerToken, verifyAccountDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(loginService, times(1)).verifyAccount(bearerToken, verifyAccountDTO);
    }

    // Additional test methods for other endpoints

    private VerifyAccountDTO createVerifyAccountDTO() {
        VerifyAccountDTO verifyAccountDTO = new VerifyAccountDTO();
        verifyAccountDTO.setOtp("123456");
        return verifyAccountDTO;
    }


    @Test
    void forgetPasswordRequest_Success() throws Exception {
        // Arrange
        ForgetPasswordDTO forgetPasswordDTO = createForgetPasswordDTO();
        when(loginService.forgetPasswordRequest(forgetPasswordDTO)).thenReturn(createClaims());
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());


        // Act
        ResponseEntity<?> response = authenticationController.forgetPasswordRequest(forgetPasswordDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(loginService, times(1)).forgetPasswordRequest(forgetPasswordDTO);
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void forgetPasswordRequest_InternalServerError() throws Exception {
        // Arrange
        ForgetPasswordDTO forgetPasswordDTO = createForgetPasswordDTO();
        when(loginService.forgetPasswordRequest(forgetPasswordDTO)).thenThrow(new Exception("Internal Server Error"));

        // Act
        ResponseEntity<?> response = authenticationController.forgetPasswordRequest(forgetPasswordDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(loginService, times(1)).forgetPasswordRequest(forgetPasswordDTO);
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    // Additional test methods for other endpoints

    private ForgetPasswordDTO createForgetPasswordDTO() {
        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        forgetPasswordDTO.setPassword("newTestPassword");
        return forgetPasswordDTO;
    }


    @Test
    void forgetPasswordVerification_Success() throws Exception {
        // Arrange
        String bearerToken = "BearerToken";
        ForgetPasswordDTO forgetPasswordDTO = createForgetPasswordDTO();
        when(loginService.forgetPasswordVerification(bearerToken, forgetPasswordDTO)).thenReturn(createClaims());

        // Act
        ResponseEntity<?> response = authenticationController.forgetPasswordVerification(bearerToken, forgetPasswordDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(loginService, times(1)).forgetPasswordVerification(bearerToken, forgetPasswordDTO);
    }

    @Test
    void forgetPasswordVerification_InternalServerError() throws Exception {
        // Arrange
        String bearerToken = "BearerToken";
        ForgetPasswordDTO forgetPasswordDTO = createForgetPasswordDTO();
        when(loginService.forgetPasswordVerification(bearerToken, forgetPasswordDTO)).thenThrow(new Exception("Internal Server Error"));

        // Act
        ResponseEntity<?> response = authenticationController.forgetPasswordVerification(bearerToken, forgetPasswordDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(loginService, times(1)).forgetPasswordVerification(bearerToken, forgetPasswordDTO);
    }

    // Additional test methods for other endpoints
}

package com.asdc.dalbites.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asdc.dalbites.exception.AccountNotVerifiedException;
import com.asdc.dalbites.exception.DuplicateEntryException;
import com.asdc.dalbites.exception.InvalidCredentialException;
import com.asdc.dalbites.model.DTO.ForgetPasswordDTO;
import com.asdc.dalbites.model.DTO.UserLoginDTO;
import com.asdc.dalbites.model.DTO.UserSignUpDTO;
import com.asdc.dalbites.model.DTO.VerifyAccountDTO;
import com.asdc.dalbites.service.LoginService;
import com.asdc.dalbites.service.impl.EmailServiceImpl;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private LoginService loginService;

	@Autowired
	private EmailServiceImpl emailService;

    @PostMapping("/signup")
    public ResponseEntity<?> doSignup(@RequestBody UserSignUpDTO userSignUpDTO) throws Exception {
		try {
			HashMap<String, Object> claims = loginService.create(userSignUpDTO);
			emailService.sendEmail((String) claims.get("email"), "Verify your account - DalBites", "Yout OTP for account verification is: " + claims.get("otp"));
			return ResponseEntity.status(HttpStatus.CREATED).body(claims);
		} catch(DuplicateEntryException error) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		} catch(Exception error) {
			return ResponseEntity.internalServerError().body(error);
		}
    }

    @PostMapping("/login")
	public ResponseEntity<?> doLogin(@RequestBody UserLoginDTO userLoginDTO) throws Exception {
		try {
			authenticate(userLoginDTO.getUsername(), userLoginDTO.getPassword());
			return ResponseEntity.ok(loginService.login(userLoginDTO));
		} catch (InvalidCredentialException error) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
		} catch (AccountNotVerifiedException error) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error); 
		} catch(Exception error) {
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@PostMapping("/verify-account")
	public ResponseEntity<?> verifyAccount(@RequestHeader("Authorization") String bearerToken, @RequestBody VerifyAccountDTO verifyAccountDTO) throws Exception {
		try {
			return ResponseEntity.ok(loginService.verifyAccount(bearerToken, verifyAccountDTO));
		} catch(Exception error) {
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@PostMapping("/forget-password-request")
	public ResponseEntity<?> forgetPasswordRequest(@RequestBody ForgetPasswordDTO forgetPasswordDTO) throws Exception {
		try {
			HashMap<String, Object> tokenClaims = loginService.forgetPasswordRequest(forgetPasswordDTO);
			emailService.sendEmail((String) tokenClaims.get("email"), "Forget Password Request - DalBites", "Your OTP for forget password is: " + tokenClaims.get("otp"));
			HashMap<String, Object> responseClaims = new HashMap<String, Object>();
			responseClaims.put("token", tokenClaims.get("token"));
			responseClaims.put("email", tokenClaims.get("email"));
			return ResponseEntity.ok(responseClaims);
		} catch(Exception error) {
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@PostMapping("/forget-password-verification")
	public ResponseEntity<?> forgetPasswordVerification(@RequestHeader("Authorization") String bearerToken, @RequestBody ForgetPasswordDTO forgetPasswordDTO) throws Exception {
		try {
			return ResponseEntity.ok(loginService.forgetPasswordVerification(bearerToken, forgetPasswordDTO));
		} catch(Exception error) {
			return ResponseEntity.internalServerError().body(error);
		}
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("User is disabled", e);
		} catch (BadCredentialsException e) {
			throw new InvalidCredentialException("Invalid Credentials", e);
		}
	}
}
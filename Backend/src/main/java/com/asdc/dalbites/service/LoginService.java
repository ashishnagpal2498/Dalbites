package com.asdc.dalbites.service;

import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DTO.ForgetPasswordDTO;
import com.asdc.dalbites.model.DTO.UserLoginDTO;
import com.asdc.dalbites.model.DTO.UserSignUpDTO;
import com.asdc.dalbites.model.DTO.VerifyAccountDTO;

@Service
public interface LoginService extends UserDetailsService {
    public HashMap<String, Object> create(UserSignUpDTO userSignUpDTO) throws Exception;

    public Object getUserByUsername(String username);

    public Object login(UserLoginDTO userLoginDTO) throws Exception;

    public Object verifyAccount(String bearerToken, VerifyAccountDTO verifyAccountDTO) throws Exception;

    public HashMap<String, Object> forgetPasswordRequest(ForgetPasswordDTO forgetPasswordDTO) throws Exception;;

    public HashMap<String, Object> forgetPasswordVerification(String bearerToken, ForgetPasswordDTO forgetPasswordDTO) throws Exception;
}

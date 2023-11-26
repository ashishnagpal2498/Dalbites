package com.asdc.dalbites.service.impl;

import java.util.List;
import java.util.Optional;

import com.asdc.dalbites.service.UserService;
import com.asdc.dalbites.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Override
    public List<UserDao> getAllUsers() {
        return (List<UserDao>)userRepository.findAll();
    }

    @Override
    public UserDao getUserById(String token){
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(7));
        String userId = tokenClaims.get("user_id").toString();
        Optional<UserDao> user = userRepository.findById(userId);
        return user.orElse(null);
    }
}

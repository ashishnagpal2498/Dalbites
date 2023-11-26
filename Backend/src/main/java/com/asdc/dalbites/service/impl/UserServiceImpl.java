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

import com.asdc.dalbites.util.Constants;

/**
 * Implementation of the {@link UserService} interface providing methods for managing user information.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtUtil;

    /**
     * Retrieves a list of all users in the system.
     *
     * @return A list of {@link UserDao} representing all users in the system.
     */
    @Override
    public List<UserDao> getAllUsers() {
        return (List<UserDao>)userRepository.findAll();
    }

    /**
     * Retrieves a user based on the provided authentication token.
     *
     * @param token  The authentication token used to identify the user.
     * @return A {@link UserDao} representing the user identified by the token, or null if not found.
     */
    @Override
    public UserDao getUserById(String token){
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(Constants.TOKEN_START_INDEX));
        String userId = tokenClaims.get("user_id").toString();
        Optional<UserDao> user = userRepository.findById(userId);
        return user.orElse(null);
    }
}

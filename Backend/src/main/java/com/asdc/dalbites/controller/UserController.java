package com.asdc.dalbites.controller;

import java.util.List;

import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling user-related operations.
 * This class provides endpoints for retrieving all users and
 * retrieving a user by their ID.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Retrieves a list of all users in the system.
     *
     * @return List of user details.
     */
    @GetMapping("/users")
    public List<UserDao> getUsers(){
        return userService.getAllUsers();
    }

    /**
     * Retrieves details of a user by their ID.
     *
     * @param bearerToken The authentication token in the request header.
     * @return ResponseEntity with the user details or a not found status.
     */
    @GetMapping("/user")
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization") String bearerToken){
        UserDao user = userService.getUserById(bearerToken);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}

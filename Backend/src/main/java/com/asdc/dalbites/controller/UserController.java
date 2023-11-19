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

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/users")
    public List<UserDao> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization") String bearerToken){
        UserDao user = userService.getUserById(bearerToken);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}

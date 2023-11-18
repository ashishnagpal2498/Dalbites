package com.asdc.dalbites.controller;

import java.util.List;

import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}

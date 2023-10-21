package com.asdc.dalbites.service.impl;

import java.util.List;

import com.asdc.dalbites.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository usersrepository;

    @Override
    public List<UserDao> getAllUsers() {
        return (List<UserDao>)usersrepository.findAll();
    }
}

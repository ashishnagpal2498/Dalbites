package com.asdc.dalbites.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DAO.UserDao;

@Service
public interface UserService {

    public List<UserDao> getAllUsers();
}

package com.asdc.dalbites.controller;

import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUsers() {
        List<UserDao> userDaos = Arrays.asList(
                new UserDao(),
                new UserDao()
        );

        when(userService.getAllUsers()).thenReturn(userDaos);

        List<UserDao> result = userController.getUsers();

        assertNotNull(result);
        assertEquals(userDaos.size(), result.size());
    }
    @Test
    void testGetUserById() {
        String bearerToken = "userToken";
        UserDao userDao = new UserDao();

        when(userService.getUserById(bearerToken)).thenReturn(userDao);

        ResponseEntity<?> result = userController.getUserById(bearerToken);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userDao, result.getBody());
    }
    @Test
    void testGetUserByIdWhenUserNotFound() {
        String bearerToken = "userTokenValue";

        when(userService.getUserById(bearerToken)).thenReturn(null);

        ResponseEntity<?> result = userController.getUserById(bearerToken);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("User not found", result.getBody());
    }
}
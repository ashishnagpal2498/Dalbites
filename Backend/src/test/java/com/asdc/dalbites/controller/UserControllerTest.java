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
}
package com.asdc.dalbites.service.impl;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.repository.UserRepository;
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

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<UserDao> userDaos = Arrays.asList(
                new UserDao(),
                new UserDao()
        );
        when(userRepository.findAll()).thenReturn(userDaos);

        List<UserDao> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(userDaos.size(), result.size());
    }
}

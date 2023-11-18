package com.asdc.dalbites.service.impl;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.repository.UserRepository;
import com.asdc.dalbites.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

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
    @Test
    void testGetUserById() {
        String token = "userToken";
        Claims claims = new DefaultClaims();
        claims.put("user_id", "46");
        Optional<UserDao> userDaoOptional = Optional.of(new UserDao());

        when(jwtTokenUtil.getAllClaimsFromToken(anyString())).thenReturn(claims);
        when(userRepository.findById(anyString())).thenReturn(userDaoOptional);

        UserDao result = userService.getUserById(token);

        assertNotNull(result);
        assertEquals(userDaoOptional.get(),result);
    }
}

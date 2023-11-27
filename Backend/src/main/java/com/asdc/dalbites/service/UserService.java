package com.asdc.dalbites.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DAO.UserDao;
/**
 * Service interface for user-related operations.
 */
@Service
public interface UserService {
    /**
     * Retrieves a list of all users.
     *
     * @return A list of {@link UserDao} representing all users.
     */
    public List<UserDao> getAllUsers();

    /**
     * Retrieves user details based on the provided authentication token.
     *
     * @param token The authentication token.
     * @return A {@link UserDao} representing the user details.
     */
    public UserDao getUserById(String token);
}

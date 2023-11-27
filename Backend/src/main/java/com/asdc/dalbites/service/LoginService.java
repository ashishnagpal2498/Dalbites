package com.asdc.dalbites.service;

import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DTO.ForgetPasswordDTO;
import com.asdc.dalbites.model.DTO.UserLoginDTO;
import com.asdc.dalbites.model.DTO.UserSignUpDTO;
import com.asdc.dalbites.model.DTO.VerifyAccountDTO;

/**
 * Service interface for user authentication and related operations.
 * Extends {@link UserDetailsService} for user details retrieval.
 */
@Service
public interface LoginService extends UserDetailsService {
    /**
     * Creates a new user account based on the provided sign-up details.
     *
     * @param userSignUpDTO The data transfer object containing user sign-up details.
     * @return A {@link HashMap} containing relevant information, typically user claims.
     * @throws Exception If an error occurs during the account creation process.
     */

    public HashMap<String, Object> create(UserSignUpDTO userSignUpDTO) throws Exception;

    /**
     * Retrieves user information by username.
     *
     * @param username The username of the user to be retrieved.
     * @return The user information.
     */
    public Object getUserByUsername(String username);

    /**
     * Authenticates a user based on the provided login details.
     *
     * @param userLoginDTO The data transfer object containing user login details.
     * @return The result of the login operation, typically user information or an error message.
     * @throws Exception If an error occurs during the authentication process.
     */
    public Object login(UserLoginDTO userLoginDTO) throws Exception;

    /**
     * Verifies a user account based on the provided verification details.
     *
     * @param bearerToken      The authentication token associated with the user.
     * @param verifyAccountDTO The data transfer object containing verification details.
     * @return The result of the account verification operation.
     * @throws Exception If an error occurs during the verification process.
     */
    public Object verifyAccount(String bearerToken, VerifyAccountDTO verifyAccountDTO) throws Exception;

    /**
     * Initiates a forget password request for a user.
     *
     * @param forgetPasswordDTO The data transfer object containing forget password details.
     * @return A {@link HashMap} containing relevant information, typically a verification token.
     * @throws Exception If an error occurs during the forget password request process.
     */
    public HashMap<String, Object> forgetPasswordRequest(ForgetPasswordDTO forgetPasswordDTO) throws Exception;

    /**
     * Verifies a forget password request based on the provided details.
     *
     * @param bearerToken        The authentication token associated with the user.
     * @param forgetPasswordDTO  The data transfer object containing forget password details.
     * @return The result of the forget password verification operation.
     * @throws Exception If an error occurs during the forget password verification process.
     */
    public HashMap<String, Object> forgetPasswordVerification(String bearerToken, ForgetPasswordDTO forgetPasswordDTO) throws Exception;
}

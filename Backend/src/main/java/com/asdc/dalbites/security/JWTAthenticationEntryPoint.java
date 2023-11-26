package com.asdc.dalbites.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JWTAthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Invoked when an unauthenticated user attempts to access a protected resource.
     * Sets the HTTP response status code to {@code 401 - Unauthorized}.
     *
     * @param request       the HttpServletRequest representing the incoming request
     * @param response      the HttpServletResponse representing the outgoing response
     * @param authException the AuthenticationException that occurred during the authentication process
     * @throws IOException      if an I/O error occurs during the processing of the request
     * @throws ServletException if a servlet-specific error occurs during the processing of the request
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

package com.asdc.dalbites.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.asdc.dalbites.service.LoginService;
import com.asdc.dalbites.util.JwtTokenUtil;
import com.asdc.dalbites.util.Constants;

/**
 * Filter to handle JWT authentication for each incoming request.
 * This class extends {@link OncePerRequestFilter} and is responsible for
 * extracting the JWT token from the request, validating it, and setting
 * the authentication details in the Spring Security context.
 * @see Component
 * @see OncePerRequestFilter
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwtTokenUtil jwtHelper;

    @Autowired
    private LoginService userDetailsService;

    /**
     * Performs the actual filtering logic for each incoming request.
     *
     * @param request     the HttpServletRequest representing the incoming request
     * @param response    the HttpServletResponse representing the outgoing response
     * @param filterChain the FilterChain for executing the next filters in the chain
     * @throws ServletException if a servlet-specific error occurs during the processing of the request
     * @throws IOException      if an I/O error occurs during the processing of the request
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(Constants.TOKEN_START_INDEX);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("Validation of JWT Token failed !!");
            }
        }
        filterChain.doFilter(request, response);
    }
}
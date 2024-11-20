package com.openclassroom.apiRentalApp.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.apiRentalApp.models.User;
import com.openclassroom.apiRentalApp.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), Collections.emptyList());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new AuthenticationException("Failed to parse authentication request body") {
                @Override
                public String getMessage() {
                    return e.getMessage();
                }
            };
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String token = jwtService.generateToken(authResult);
        response.addHeader("Authorization", "Bearer " + token);
    }
}
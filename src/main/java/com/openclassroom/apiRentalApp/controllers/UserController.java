package com.openclassroom.apiRentalApp.controllers;

import com.openclassroom.apiRentalApp.models.User;
import com.openclassroom.apiRentalApp.services.JWTService;
import com.openclassroom.apiRentalApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;

    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        userService.loginUser(user.getEmail(), user.getPassword());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwtService.generateToken(authentication);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/public/info")
    public ResponseEntity<String> publicInfo() {
        return ResponseEntity.ok("Public information");
    }

    @GetMapping("/private/info")
    public ResponseEntity<String> privateInfo(Authentication authentication) {
        return ResponseEntity.ok("Private information for " + authentication.getName());
    }
}
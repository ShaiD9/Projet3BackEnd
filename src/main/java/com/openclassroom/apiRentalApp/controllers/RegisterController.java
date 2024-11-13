package com.openclassroom.apiRentalApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.apiRentalApp.models.User;
import com.openclassroom.apiRentalApp.services.UserService;

@RestController
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userService.userExists(user.getName())) {
            return "User already exists";
        }
        userService.registerUser(user.getName(), user.getPassword(), user.getEmail());
        return "User registered successfully";
    }
}
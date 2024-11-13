package com.openclassroom.apiRentalApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.apiRentalApp.models.User;
import com.openclassroom.apiRentalApp.services.JWTService;
import com.openclassroom.apiRentalApp.services.UserService;

@RestController
public class LoginController {

	private JWTService jwtService;
	private final UserService userService;

	public LoginController(JWTService jwtService, UserService userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@PostMapping("/login")
	public String getToken(Authentication authentication,@RequestBody User user) {
		userService.loginUser(user.getEmail(), user.getName());
		String token = jwtService.generateToken(authentication);
		return token;
	}
}

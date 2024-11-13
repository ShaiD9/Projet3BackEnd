package com.openclassroom.apiRentalApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassroom.apiRentalApp.models.User;
import com.openclassroom.apiRentalApp.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String name, String password, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setName(name);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        userRepository.save(user);
    }

    public boolean userExists(String name) {
        return userRepository.findByName(name) != null;
    }

    public String getPassword(String name) {
        User user = userRepository.findByName(name);
        return user != null ? user.getPassword() : null;
    }
}
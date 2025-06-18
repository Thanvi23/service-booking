package com.example.servicebooking.service;

import com.example.servicebooking.model.User;

import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> findByEmail(String email);
}

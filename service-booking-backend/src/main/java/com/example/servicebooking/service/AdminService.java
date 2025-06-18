package com.example.servicebooking.service;

import com.example.servicebooking.model.ServiceProvider;
import com.example.servicebooking.model.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    List<ServiceProvider> getAllProviders();
    String approveProvider(Long providerId);
}

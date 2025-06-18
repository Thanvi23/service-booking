package com.example.servicebooking.controller;

import com.example.servicebooking.model.ServiceProvider;
import com.example.servicebooking.model.User;
import com.example.servicebooking.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/providers")
    public ResponseEntity<List<ServiceProvider>> getAllProviders() {
        return ResponseEntity.ok(adminService.getAllProviders());
    }

    @PutMapping("/approve/{providerId}")
    public ResponseEntity<String> approveProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(adminService.approveProvider(providerId));
    }
}

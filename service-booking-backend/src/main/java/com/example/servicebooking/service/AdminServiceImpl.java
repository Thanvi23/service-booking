package com.example.servicebooking.service;

import com.example.servicebooking.model.ServiceProvider;
import com.example.servicebooking.model.User;
import com.example.servicebooking.repository.ServiceProviderRepository;
import com.example.servicebooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<ServiceProvider> getAllProviders() {
        return serviceProviderRepository.findAll();
    }

    @Override
    public String approveProvider(Long providerId) {
        ServiceProvider provider = serviceProviderRepository.findById(providerId).orElseThrow();
        provider.setStatus("APPROVED");
        serviceProviderRepository.save(provider);
        return "Provider approved successfully!";
    }
}

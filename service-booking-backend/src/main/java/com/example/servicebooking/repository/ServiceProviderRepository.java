package com.example.servicebooking.repository;

import com.example.servicebooking.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    List<ServiceProvider> findByCategoryId(Long categoryId);
    List<ServiceProvider> findByStatus(String status);
}

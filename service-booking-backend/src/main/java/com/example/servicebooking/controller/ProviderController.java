package com.example.servicebooking.controller;

import com.example.servicebooking.model.Booking;
import com.example.servicebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/bookings/{providerId}")
    public ResponseEntity<List<Booking>> viewBookings(@PathVariable Long providerId) {
        return ResponseEntity.ok(bookingService.getBookingsByProviderId(providerId));
    }

    @PutMapping("/bookings/{bookingId}/status")
    public ResponseEntity<Booking> updateStatus(@PathVariable Long bookingId, @RequestParam String status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(bookingId, status));
    }
}

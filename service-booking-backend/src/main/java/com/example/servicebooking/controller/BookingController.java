package com.example.servicebooking.controller;

import com.example.servicebooking.model.Booking;
import com.example.servicebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<Booking>> getBookingsByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(bookingService.getBookingsByProviderId(providerId));
    }
}

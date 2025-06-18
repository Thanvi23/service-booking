package com.example.servicebooking.service;

import com.example.servicebooking.model.Booking;
import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    List<Booking> getBookingsByUserId(Long userId);
    List<Booking> getBookingsByProviderId(Long providerId);
    Booking updateBookingStatus(Long bookingId, String status);
}


package com.example.booking.booking;

import com.example.booking.booking.dto.BookingRequest;
import com.example.booking.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository repository;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        try {
            String result = bookingService.createBooking(request);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    @GetMapping("/guest/{guestId}")
    public List<Booking> getBookingsByGuest(@PathVariable String guestId) {
        return repository.findByGuestId(guestId);
    }
}
package com.example.booking.booking.service;

import com.example.booking.booking.client.GuestClient;
import com.example.booking.booking.client.RoomClient;
import com.example.booking.booking.dto.BookingRequest;
import com.example.booking.booking.dto.GuestResponse;
import com.example.booking.booking.Booking;
import com.example.booking.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final GuestClient guestClient;
    private final RoomClient roomClient;
    private final BookingRepository bookingRepository;

    public String createBooking(BookingRequest request) {
        // 1. VALIDATE GUEST (Read-only)
        var guest = guestClient.getGuest(request.getGuestId());
        if (guest == null) {
            throw new RuntimeException("Guest not found: " + request.getGuestId());
        }

        // 2. RESERVE ROOM (Write 1)
        boolean roomReserved = roomClient.reserveRoom(request.getRoomId());
        if (!roomReserved) {
            throw new RuntimeException("Room " + request.getRoomId() + " is already occupied.");
        }

        try {
            // 3. PAYMENT (Mock External Payment Service)
            // Simulating a failure for a specific guest lastName (e.g., "BadPayer")
            if ("BadPayer".equalsIgnoreCase(guest.getLastName())) {
                throw new RuntimeException("Payment Rejected: Insufficient Funds");
            }

            // 4. FINALIZE (Write 2 - Local DB)
            Booking booking = new Booking();
            booking.setGuestId(request.getGuestId());
            booking.setRoomId(request.getRoomId());
            booking.setStatus("CONFIRMED");
            booking.setCheckInDate(request.getCheckInDate());
            booking.setCheckOutDate(request.getCheckOutDate());

            bookingRepository.save(booking);

            return "Booking Confirmed!";

        } catch (Exception e) {
            // *** ROLLBACK (COMPENSATING TRANSACTION) ***
            System.err.println("Error during booking: " + e.getMessage());
            System.err.println("Rolling back room reservation for Room ID: " + request.getRoomId());

            // Unlock the room we just locked
            roomClient.releaseRoom(request.getRoomId());

            throw new RuntimeException("Transaction Failed & Rolled Back: " + e.getMessage());
        }
    }
}
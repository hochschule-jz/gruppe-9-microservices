package com.microservice.hotel.booking;

import com.microservice.hotel.room.Room;
import com.microservice.hotel.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    public Booking createBooking(Booking booking) {
        Room room = roomRepository.findById(booking.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + booking.getRoomId()));

        LocalDate checkInDate = booking.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOutDate = booking.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        // Ensure at least one night is booked
        if (numberOfNights <= 0) {
            numberOfNights = 1;
        }

        double totalPrice = room.getPricePerNight() * numberOfNights;

        booking.setTotalPrice(totalPrice);
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(String id, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        booking.setCheckInDate(bookingDetails.getCheckInDate());
        booking.setCheckOutDate(bookingDetails.getCheckOutDate());
        booking.setGuestId(bookingDetails.getGuestId());
        booking.setRoomId(bookingDetails.getRoomId());
        booking.setTotalPrice(bookingDetails.getTotalPrice());
        booking.setStatus(bookingDetails.getStatus());

        return bookingRepository.save(booking);
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
}

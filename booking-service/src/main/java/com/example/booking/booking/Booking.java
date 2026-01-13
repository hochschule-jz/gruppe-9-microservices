package com.example.booking.booking;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDate;

@Data
@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;

    private String roomId;
    private String guestId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private String status;
}
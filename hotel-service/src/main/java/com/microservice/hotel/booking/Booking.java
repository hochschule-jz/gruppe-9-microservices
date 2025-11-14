package com.microservice.hotel.booking;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Represents a booking connecting a guest with a room for specific dates
 */
@Getter
@Setter
@Document
public class Booking {
    @Id
    private String id;

    @CreatedDate
    private Date createdAt;

    private Date checkInDate;

    private Date checkOutDate;

    private String guestId;

    private String roomId;

    private double totalPrice;

    private String status;
}

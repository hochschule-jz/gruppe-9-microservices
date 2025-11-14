package com.microservice.hotel.room;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Represents a hotel room available for booking
 */
@Getter
@Setter
@Document
public class Room {
    @Id
    private String id;

    private String roomNumber;

    private String type;

    private int capacity;

    private double pricePerNight;

    private boolean available;

    private List<String> bookings;
}

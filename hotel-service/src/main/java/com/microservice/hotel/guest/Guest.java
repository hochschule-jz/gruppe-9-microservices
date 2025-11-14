package com.microservice.hotel.guest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Represents a guest in the hotel reservation system
 */
@Getter
@Setter
@Document
public class Guest {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<String> bookings;
}

package com.example.booking.booking.dto;

import lombok.Data;

@Data
public class GuestResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
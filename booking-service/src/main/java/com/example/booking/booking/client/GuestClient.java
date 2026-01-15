package com.example.booking.booking.client;

import com.example.booking.booking.dto.GuestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "guest-service")
public interface GuestClient {

    @GetMapping("/guests/{id}")
    GuestResponse getGuest(@PathVariable String id);
}
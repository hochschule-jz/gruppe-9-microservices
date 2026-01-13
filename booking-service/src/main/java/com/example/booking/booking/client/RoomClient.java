package com.example.booking.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "room-service")
public interface RoomClient {
    @PostMapping("/rooms/{id}/reserve")
    boolean reserveRoom(@PathVariable String id);

    @PostMapping("/rooms/{id}/release")
    void releaseRoom(@PathVariable String id);
}
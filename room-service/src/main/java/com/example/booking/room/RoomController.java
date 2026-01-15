package com.example.booking.room;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomRepository roomRepository;

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomRepository.save(room);
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable String id) {
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Lock Room (Saga Step)
    @PostMapping("/{id}/reserve")
    public boolean reserveRoom(@PathVariable String id) {
        Room room = roomRepository.findById(id).orElseThrow();
        if (room.isAvailable()) {
            room.setAvailable(false);
            roomRepository.save(room);
            return true;
        }
        return false;
    }

    // Unlock Room (Saga Rollback)
    @PostMapping("/{id}/release")
    public void releaseRoom(@PathVariable String id) {
        Room room = roomRepository.findById(id).orElseThrow();
        room.setAvailable(true);
        roomRepository.save(room);
    }
}
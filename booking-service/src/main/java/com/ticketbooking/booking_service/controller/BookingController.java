package com.ticketbooking.booking_service.controller;

import com.ticketbooking.booking_service.dto.SeatLockRequestDto;
import com.ticketbooking.booking_service.exception.SeatAlreadyLockedException;
import com.ticketbooking.booking_service.service.SeatLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final SeatLockService seatLockService;

    @PostMapping("/lock")
    public ResponseEntity<Map<String, String>> lockSeat(@RequestBody SeatLockRequestDto request) {
        boolean locked = seatLockService.tryLock(
                request.getEventId(), request.getSeatId(), request.getUserId());

        if (!locked) {
            throw new SeatAlreadyLockedException(
                    "Seat " + request.getSeatId() + " is already locked by another user");
        }
        return ResponseEntity.ok(Map.of("message", "Seat locked for 5 minutes"));
    }
}
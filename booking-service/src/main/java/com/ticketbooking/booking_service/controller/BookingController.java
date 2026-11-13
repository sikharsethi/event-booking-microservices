package com.ticketbooking.booking_service.controller;

import com.ticketbooking.booking_service.dto.SeatDetailsDto;
import com.ticketbooking.booking_service.dto.SeatLockRequestDto;
import com.ticketbooking.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/lock")
    public ResponseEntity<Map<String, Object>> lockSeat(@RequestBody SeatLockRequestDto request) {
        SeatDetailsDto seat = bookingService.reserveSeat(request);
        return ResponseEntity.ok(Map.of(
                "message", "Seat locked for 5 minutes",
                "seatNumber", seat.getSeatNumber(),
                "amount", seat.getTicketPrice()));
    }
}
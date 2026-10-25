package com.ticketbooking.booking_service.dto;

import lombok.Data;

@Data
public class SeatLockRequestDto {
    private Long eventId;
    private Long seatId;
    private Long userId;
}
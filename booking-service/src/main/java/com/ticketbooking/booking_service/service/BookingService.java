package com.ticketbooking.booking_service.service;

import com.ticketbooking.booking_service.client.CatalogClient;
import com.ticketbooking.booking_service.dto.SeatDetailsDto;
import com.ticketbooking.booking_service.dto.SeatLockRequestDto;
import com.ticketbooking.booking_service.exception.SeatAlreadyLockedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final CatalogClient catalogClient;
    private final SeatLockService seatLockService;

    public SeatDetailsDto reserveSeat(SeatLockRequestDto request) {
        SeatDetailsDto seat = catalogClient.getSeat(request.getEventId(), request.getSeatId());

        boolean locked = seatLockService.tryLock(
                request.getEventId(), request.getSeatId(), request.getUserId());
        if (!locked) {
            throw new SeatAlreadyLockedException(
                    "Seat " + request.getSeatId() + " is already locked by another user");
        }
        return seat;
    }
}
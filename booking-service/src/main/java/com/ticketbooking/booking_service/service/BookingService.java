package com.ticketbooking.booking_service.service;

import com.ticketbooking.booking_service.client.CatalogClient;
import com.ticketbooking.booking_service.dto.SeatDetailsDto;
import com.ticketbooking.booking_service.dto.SeatLockRequestDto;
import com.ticketbooking.booking_service.exception.SeatAlreadyLockedException;
import com.ticketbooking.booking_service.model.Booking;
import com.ticketbooking.booking_service.model.BookingStatus;
import com.ticketbooking.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final CatalogClient catalogClient;
    private final SeatLockService seatLockService;
    private final BookingRepository bookingRepository;

    public Booking reserveSeat(SeatLockRequestDto request) {
        SeatDetailsDto seat = catalogClient.getSeat(request.getEventId(), request.getSeatId());

        boolean locked = seatLockService.tryLock(
                request.getEventId(), request.getSeatId(), request.getUserId());
        if (!locked) {
            throw new SeatAlreadyLockedException(
                    "Seat " + request.getSeatId() + " is already locked by another user");
        }

        try {

            Booking booking = Booking.builder()
                    .eventId(request.getEventId())
                    .seatId(request.getSeatId())
                    .userId(request.getUserId())
                    .status(BookingStatus.PENDING)
                    .totalAmount(seat.getTicketPrice())
                    .lockExpirationTime(LocalDateTime.now().plus(SeatLockService.LOCK_TTL))
                    .build();
            return bookingRepository.save(booking);
        } catch (RuntimeException e) {
            seatLockService.releaseLock(
                    request.getEventId(), request.getSeatId(), request.getUserId());
            throw e;
        }
    }
}
package com.ticketbooking.booking_service.repository;

import com.ticketbooking.booking_service.model.Booking;
import com.ticketbooking.booking_service.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStatusAndLockExpirationTimeBefore(BookingStatus status, LocalDateTime time);


}

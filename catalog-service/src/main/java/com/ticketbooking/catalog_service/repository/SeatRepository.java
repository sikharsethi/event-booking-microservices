package com.ticketbooking.catalog_service.repository;

import com.ticketbooking.catalog_service.model.Seat;
import com.ticketbooking.catalog_service.model.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByEventId(Long eventId);
    List<Seat> findByEventIdAndStatus(Long eventId, SeatStatus status);
}
package com.ticketbooking.catalog_service.repository;

import com.ticketbooking.catalog_service.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByEventId(Long eventId);
    Optional<Seat> findByIdAndEventId(Long id, Long eventId);
}
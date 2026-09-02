package com.ticketbooking.catalog_service.repository;

import com.ticketbooking.catalog_service.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
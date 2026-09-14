package com.ticketbooking.catalog_service.controller;

import com.ticketbooking.catalog_service.dto.EventCreateRequestDto;
import com.ticketbooking.catalog_service.dto.EventResponseDto;
import com.ticketbooking.catalog_service.dto.SeatResponseDto;
import com.ticketbooking.catalog_service.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventCreateRequestDto requestDto) {
        EventResponseDto responseDto = eventService.createEvent(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        List<EventResponseDto> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{eventId}/seats")
    public ResponseEntity<List<SeatResponseDto>> getSeatsByEventId(@PathVariable Long eventId) {
        List<SeatResponseDto> seats = eventService.getSeatsByEventId(eventId);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }
}
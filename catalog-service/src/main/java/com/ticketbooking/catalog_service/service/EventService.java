package com.ticketbooking.catalog_service.service;

import com.ticketbooking.catalog_service.dto.EventCreateRequestDto;
import com.ticketbooking.catalog_service.dto.EventResponseDto;
import com.ticketbooking.catalog_service.dto.SeatResponseDto;
import com.ticketbooking.catalog_service.exception.EventNotFoundException;
import com.ticketbooking.catalog_service.model.Event;
import com.ticketbooking.catalog_service.model.Seat;
import com.ticketbooking.catalog_service.model.SeatStatus;
import com.ticketbooking.catalog_service.repository.EventRepository;
import com.ticketbooking.catalog_service.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;

    public EventResponseDto createEvent(EventCreateRequestDto requestDto) {
        // Step 1: Event banao aur save karo
        Event event = new Event();
        event.setName(requestDto.getName());
        event.setVenue(requestDto.getVenue());
        event.setEventDateTime(requestDto.getEventDateTime());
        Event savedEvent = eventRepository.save(event);

        // Step 2: Seats generate karo (A1, A2, A3...)
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= requestDto.getTotalSeats(); i++) {
            Seat seat = new Seat();
            seat.setSeatNumber("A" + i);
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setTicketPrice(requestDto.getTicketPrice());
            seat.setEvent(savedEvent);
            seats.add(seat);
        }
        seatRepository.saveAll(seats);

        // Step 3: Response DTO banao
        EventResponseDto responseDto = new EventResponseDto();
        responseDto.setId(savedEvent.getId());
        responseDto.setName(savedEvent.getName());
        responseDto.setVenue(savedEvent.getVenue());
        responseDto.setEventDateTime(savedEvent.getEventDateTime());
        responseDto.setTotalSeats(requestDto.getTotalSeats());
        responseDto.setAvailableSeats(requestDto.getTotalSeats());

        return responseDto;
    }

    public List<EventResponseDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventResponseDto> responseDtos = new ArrayList<>();

        for (Event event : events) {
            EventResponseDto dto = new EventResponseDto();
            dto.setId(event.getId());
            dto.setName(event.getName());
            dto.setVenue(event.getVenue());
            dto.setEventDateTime(event.getEventDateTime());

            List<Seat> seats = event.getSeats();
            int totalSeats = seats.size();
            int availableSeats = 0;
            for (Seat seat : seats) {
                if (seat.getStatus() == SeatStatus.AVAILABLE) {
                    availableSeats++;
                }
            }

            dto.setTotalSeats(totalSeats);
            dto.setAvailableSeats(availableSeats);
            responseDtos.add(dto);
        }

        return responseDtos;
    }

    public List<SeatResponseDto> getSeatsByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + eventId));

        List<Seat> seats = event.getSeats();
        List<SeatResponseDto> seatResponseDtos = new ArrayList<>();

        for (Seat seat : seats) {
            SeatResponseDto dto = new SeatResponseDto();
            dto.setId(seat.getId());
            dto.setSeatNumber(seat.getSeatNumber());
            dto.setStatus(seat.getStatus());
            dto.setTicketPrice(seat.getTicketPrice());
            seatResponseDtos.add(dto);
        }

        return seatResponseDtos;
    }
}
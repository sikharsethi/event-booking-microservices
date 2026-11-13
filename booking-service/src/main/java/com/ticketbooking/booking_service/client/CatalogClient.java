package com.ticketbooking.booking_service.client;

import com.ticketbooking.booking_service.dto.SeatDetailsDto;
import com.ticketbooking.booking_service.exception.CatalogUnavailableException;
import com.ticketbooking.booking_service.exception.SeatNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.ResourceAccessException;

@Component
@RequiredArgsConstructor
public class CatalogClient {

    private final RestClient catalogRestClient;

    public SeatDetailsDto getSeat(Long eventId, Long seatId) {
        try {
            return catalogRestClient.get()
                    .uri("/events/{eventId}/seats/{seatId}", eventId, seatId)
                    .retrieve()
                    .body(SeatDetailsDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new SeatNotFoundException(
                    "Seat " + seatId + " not found for event " + eventId);
        } catch (ResourceAccessException e) {
        throw new CatalogUnavailableException(
                "Catalog service is currently unavailable, please try again shortly");
    }
    }
}
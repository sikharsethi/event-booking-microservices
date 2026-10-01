package com.ticketbooking.catalog_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventCreateRequestDto {

    @NotBlank(message = "Event name cannot be empty")
    private String name;

    @NotBlank(message = "Venue cannot be empty")
    private String venue;

    @NotNull(message = "Event date and time is required")
    @Future(message = "Event date must be in the future")
    private LocalDateTime eventDateTime;

    @Min(value = 1, message = "Total seats must be at least 1")
    private int totalSeats;

    @NotNull(message = "Ticket price is required")
    @Positive(message = "Ticket price must be greater than zero")
    private BigDecimal ticketPrice;
}
package com.ticketbooking.catalog_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventResponseDto {
    private Long id;
    private String name;
    private String venue;
    private LocalDateTime eventDateTime;
    private int totalSeats;
    private int availableSeats;
}
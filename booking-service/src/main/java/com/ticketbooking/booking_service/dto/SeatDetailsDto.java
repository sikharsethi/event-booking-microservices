package com.ticketbooking.booking_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SeatDetailsDto {
    private Long id;
    private String seatNumber;
    private String status;
    private BigDecimal ticketPrice;
}
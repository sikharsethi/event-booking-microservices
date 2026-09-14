package com.ticketbooking.catalog_service.dto;

import com.ticketbooking.catalog_service.model.SeatStatus;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SeatResponseDto {
    private Long id;
    private String seatNumber;
    private SeatStatus status;
    private BigDecimal ticketPrice;
}
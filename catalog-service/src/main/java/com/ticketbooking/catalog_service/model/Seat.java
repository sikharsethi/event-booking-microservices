package com.ticketbooking.catalog_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "seats")
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private BigDecimal ticketPrice;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
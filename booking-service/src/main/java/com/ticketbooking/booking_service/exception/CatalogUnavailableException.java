package com.ticketbooking.booking_service.exception;

public class CatalogUnavailableException extends RuntimeException {
    public CatalogUnavailableException(String message) {
        super(message);
    }
}
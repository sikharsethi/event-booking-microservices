package com.ticketbooking.booking_service.exception;

import com.ticketbooking.booking_service.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SeatAlreadyLockedException.class)
    public ResponseEntity<ErrorResponseDto> handleSeatLocked(SeatAlreadyLockedException ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleSeatNotFound(SeatNotFoundException ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CatalogUnavailableException.class)
    public ResponseEntity<ErrorResponseDto> handleCatalogUnavailable(CatalogUnavailableException ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
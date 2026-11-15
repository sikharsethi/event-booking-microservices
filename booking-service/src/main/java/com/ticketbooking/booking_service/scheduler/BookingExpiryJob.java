package com.ticketbooking.booking_service.scheduler;

import com.ticketbooking.booking_service.model.Booking;
import com.ticketbooking.booking_service.model.BookingStatus;
import com.ticketbooking.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingExpiryJob {

    private final BookingRepository bookingRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expirePendingBookings() {
        List<Booking> expired = bookingRepository
                .findByStatusAndLockExpirationTimeBefore(BookingStatus.PENDING, LocalDateTime.now());

        for (Booking booking : expired) {
            booking.setStatus(BookingStatus.EXPIRED);
        }
        bookingRepository.saveAll(expired);

        if (!expired.isEmpty()) {
            System.out.println("Expired " + expired.size() + " pending booking(s)");
        }
    }
}
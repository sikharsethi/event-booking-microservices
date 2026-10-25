package com.ticketbooking.booking_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SeatLockService {

    private static final Duration LOCK_TTL = Duration.ofMinutes(5);

    private final StringRedisTemplate redisTemplate;

    public boolean tryLock(Long eventId, Long seatId, Long userId) {
        String key = buildKey(eventId, seatId);
        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(key, String.valueOf(userId), LOCK_TTL);
        return Boolean.TRUE.equals(acquired);
    }

    private String buildKey(Long eventId, Long seatId) {
        return "seat:" + eventId + ":" + seatId;
    }
}
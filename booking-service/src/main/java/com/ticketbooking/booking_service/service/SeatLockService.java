package com.ticketbooking.booking_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatLockService {

    public static final Duration LOCK_TTL = Duration.ofMinutes(5);

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

    private static final DefaultRedisScript<Long> RELEASE_SCRIPT = new DefaultRedisScript<>(
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) else return 0 end",
            Long.class);

    public boolean releaseLock(Long eventId, Long seatId, Long userId) {
        Long result = redisTemplate.execute(
                RELEASE_SCRIPT,
                List.of(buildKey(eventId, seatId)),
                String.valueOf(userId));
        return Long.valueOf(1).equals(result);
    }

}
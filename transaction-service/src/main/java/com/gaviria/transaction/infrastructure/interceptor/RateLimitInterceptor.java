package com.gaviria.transaction.infrastructure.interceptor;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gaviria.transaction.domain.exceptions.RateLimitExceededException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, String> redisTemplate;
    private final int maxRequests;
    private final int timeWindowInSeconds;

    public RateLimitInterceptor(RedisTemplate<String, String> redisTemplate,
                                @Value("${rate.limit.maxRequests}") int maxRequests,
                                @Value("${rate.limit.timeWindowInSeconds}") int timeWindowInSeconds) {
        this.redisTemplate = redisTemplate;
        this.maxRequests = maxRequests;
        this.timeWindowInSeconds = timeWindowInSeconds;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        String key = "rate_limit:" + clientIp;

        String requestCount = redisTemplate.opsForValue().get(key);

        if (requestCount == null) {
            redisTemplate.opsForValue().set(key, "1");
            redisTemplate.expire(key, Duration.ofSeconds(timeWindowInSeconds));
        } else {
            try {
                int currentCount = Integer.parseInt(requestCount.trim());

                if (currentCount >= maxRequests) {
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    throw new RateLimitExceededException("Rate limit exceeded for IP: " + clientIp);
                } else {
                    redisTemplate.opsForValue().increment(key);
                }
            } catch (NumberFormatException e) {
                System.err.println("Valor inválido en Redis para " + key + ": " + requestCount);
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.getWriter().write("Invalid request count value");
                return false;
            }
        }

        return true;
    }

}
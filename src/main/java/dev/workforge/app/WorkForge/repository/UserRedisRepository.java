package dev.workforge.app.WorkForge.repository;

import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public interface UserRedisRepository {
    <T> void set(String key, T value, long timeout, TimeUnit timeUnit);
    void delete(String sessionId);
    boolean exists(String sessionId);
    <T> T get(String sessionId, Class<T> type);
}

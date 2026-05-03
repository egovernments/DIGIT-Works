package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.egov.digit.expense.config.Constants.TASK_CACHE_KEY_PREFIX;

/**
 * Redis cache for Task entities, keyed by tenantId + billDetailId + type.
 * Written before every Kafka push so scheduler reads see the latest task state
 * even while the Kafka→persister lag window is open.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskCacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${expense.task.cache.ttl.seconds:120}")
    private long cacheTtlSeconds;

    public void put(Task task) {
        if (task == null || task.getTenantId() == null || task.getBillDetailId() == null || task.getType() == null) return;
        try {
            String key = buildKey(task.getTenantId(), task.getBillDetailId(), task.getType());
            String json = objectMapper.writeValueAsString(task);
            redisTemplate.opsForValue().set(key, json, cacheTtlSeconds, TimeUnit.SECONDS);
            log.debug("Cached task billDetailId={} type={} status={}", task.getBillDetailId(), task.getType(), task.getStatus());
        } catch (Exception e) {
            log.warn("Failed to cache task billDetailId={}: {}", task.getBillDetailId(), e.getMessage());
        }
    }

    public Optional<Task> get(String tenantId, String billDetailId, Task.Type type) {
        if (tenantId == null || billDetailId == null || type == null) return Optional.empty();
        try {
            String val = redisTemplate.opsForValue().get(buildKey(tenantId, billDetailId, type));
            if (val == null) return Optional.empty();
            return Optional.of(objectMapper.readValue(val, Task.class));
        } catch (Exception e) {
            log.warn("Failed to read task cache billDetailId={}: {}", billDetailId, e.getMessage());
            return Optional.empty();
        }
    }

    /** Invalidate a cached task (e.g., when it reaches a terminal status). */
    public void evict(String tenantId, String billDetailId, Task.Type type) {
        if (tenantId == null || billDetailId == null || type == null) return;
        try {
            redisTemplate.delete(buildKey(tenantId, billDetailId, type));
        } catch (Exception e) {
            log.warn("Failed to evict task cache billDetailId={}: {}", billDetailId, e.getMessage());
        }
    }

    private String buildKey(String tenantId, String billDetailId, Task.Type type) {
        return TASK_CACHE_KEY_PREFIX + tenantId + ":" + billDetailId + ":" + type.name();
    }
}

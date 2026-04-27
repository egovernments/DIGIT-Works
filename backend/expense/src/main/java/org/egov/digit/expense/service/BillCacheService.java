package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.web.models.Bill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillCacheService {

    private static final String KEY_PREFIX = "bill:";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${expense.bill.cache.ttl.seconds:60}")
    private long cacheTtlSeconds;

    public void put(Bill bill) {
        if (bill == null || bill.getId() == null || bill.getTenantId() == null) return;
        try {
            String key = buildKey(bill.getTenantId(), bill.getId());
            String json = objectMapper.writeValueAsString(bill);
            redisTemplate.opsForValue().set(key, json, cacheTtlSeconds, TimeUnit.SECONDS);
            log.debug("Cached bill id={} tenantId={} ttl={}s", bill.getId(), bill.getTenantId(), cacheTtlSeconds);
        } catch (Exception e) {
            log.warn("Failed to cache bill id={}: {}", bill.getId(), e.getMessage());
        }
    }

    public Optional<Bill> get(String tenantId, String billId) {
        try {
            String val = redisTemplate.opsForValue().get(buildKey(tenantId, billId));
            if (val == null) return Optional.empty();
            return Optional.of(objectMapper.readValue(val, Bill.class));
        } catch (Exception e) {
            log.warn("Failed to read bill cache id={}: {}", billId, e.getMessage());
            return Optional.empty();
        }
    }

    public Map<String, Bill> getMultiple(String tenantId, Set<String> billIds) {
        if (billIds == null || billIds.isEmpty()) return Collections.emptyMap();
        try {
            List<String> keys = billIds.stream()
                    .map(id -> buildKey(tenantId, id))
                    .collect(Collectors.toList());
            List<String> values = redisTemplate.opsForValue().multiGet(keys);
            if (values == null) return Collections.emptyMap();

            Map<String, Bill> result = new HashMap<>();
            List<String> idList = List.copyOf(billIds);
            IntStream.range(0, idList.size()).forEach(i -> {
                String val = values.get(i);
                if (val != null) {
                    try {
                        result.put(idList.get(i), objectMapper.readValue(val, Bill.class));
                    } catch (Exception e) {
                        log.warn("Failed to deserialize cached bill id={}: {}", idList.get(i), e.getMessage());
                    }
                }
            });
            return result;
        } catch (Exception e) {
            log.warn("Failed to read bill cache for tenantId={}: {}", tenantId, e.getMessage());
            return Collections.emptyMap();
        }
    }

    private String buildKey(String tenantId, String billId) {
        return KEY_PREFIX + tenantId + ":" + billId;
    }
}

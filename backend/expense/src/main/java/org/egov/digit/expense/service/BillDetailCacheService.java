package org.egov.digit.expense.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.web.models.BillDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.egov.digit.expense.config.Constants.BILL_CACHE_KEY_PREFIX;
import static org.egov.digit.expense.config.Constants.BILL_DETAIL_CACHE_INFIX;
import static org.egov.digit.expense.config.Constants.BILL_DETAIL_IDS_CACHE_SUFFIX;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillDetailCacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${expense.bill.cache.ttl.seconds:300}")
    private long cacheTtlSeconds;

    public void putDetail(BillDetail detail, String tenantId) {
        if (detail == null || detail.getId() == null || detail.getBillId() == null || tenantId == null) return;
        try {
            String key = detailKey(tenantId, detail.getBillId(), detail.getId());
            String json = objectMapper.writeValueAsString(detail);
            redisTemplate.opsForValue().set(key, json, cacheTtlSeconds, TimeUnit.SECONDS);
            log.debug("Cached detail id={} billId={} tenantId={}", detail.getId(), detail.getBillId(), tenantId);
        } catch (Exception e) {
            log.warn("Failed to cache detail id={}: {}", detail.getId(), e.getMessage());
        }
    }

    public Optional<BillDetail> getDetail(String billId, String detailId, String tenantId) {
        if (billId == null || detailId == null || tenantId == null) return Optional.empty();
        try {
            String val = redisTemplate.opsForValue().get(detailKey(tenantId, billId, detailId));
            if (val == null) return Optional.empty();
            return Optional.of(objectMapper.readValue(val, BillDetail.class));
        } catch (Exception e) {
            log.warn("Failed to read detail cache billId={} detailId={}: {}", billId, detailId, e.getMessage());
            return Optional.empty();
        }
    }

    public void putDetailIds(String billId, String tenantId, List<String> detailIds) {
        if (billId == null || tenantId == null || detailIds == null) return;
        try {
            String key = detailIdsKey(tenantId, billId);
            String json = objectMapper.writeValueAsString(detailIds);
            redisTemplate.opsForValue().set(key, json, cacheTtlSeconds, TimeUnit.SECONDS);
            log.debug("Cached detailIds billId={} tenantId={} count={}", billId, tenantId, detailIds.size());
        } catch (Exception e) {
            log.warn("Failed to cache detailIds for billId={}: {}", billId, e.getMessage());
        }
    }

    public Optional<List<String>> getDetailIds(String billId, String tenantId) {
        if (billId == null || tenantId == null) return Optional.empty();
        try {
            String val = redisTemplate.opsForValue().get(detailIdsKey(tenantId, billId));
            if (val == null) return Optional.empty();
            List<String> ids = objectMapper.readValue(val, new TypeReference<List<String>>() {});
            return Optional.of(ids);
        } catch (Exception e) {
            log.warn("Failed to read detailIds cache billId={}: {}", billId, e.getMessage());
            return Optional.empty();
        }
    }

    private String detailKey(String tenantId, String billId, String detailId) {
        return tenantId + ":" + BILL_CACHE_KEY_PREFIX + billId + BILL_DETAIL_CACHE_INFIX + detailId;
    }

    private String detailIdsKey(String tenantId, String billId) {
        return tenantId + ":" + BILL_CACHE_KEY_PREFIX + billId + BILL_DETAIL_IDS_CACHE_SUFFIX;
    }
}

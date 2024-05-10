package org.egov.works.mukta.adapter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.works.mukta.adapter.config.Constants;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void setObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setCacheForDisbursement(Disbursement disbursement) {
        try {
            String disbursementJson = objectMapper.writeValueAsString(disbursement);
            redisTemplate.opsForValue().set(getDisburseRedisKey(disbursement.getId()), disbursementJson);
        } catch (JsonProcessingException e) {
            // Handle JSON processing exception
            log.error("Error serializing disbursement object", e);
        }
    }

    public Disbursement getDisbursementFromCache(String id) {
        String disbursementJson = (String) redisTemplate.opsForValue().get(getDisburseRedisKey(id));
        if (disbursementJson != null) {
            try {
                return objectMapper.readValue(disbursementJson, Disbursement.class);
            } catch (JsonProcessingException e) {
                // Handle JSON processing exception
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getDisburseRedisKey(String id) {
        return Constants.DISBURSE_REDIS_KEY.replace("{uuid}", id);
    }
}

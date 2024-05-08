package org.egov.works.mukta.adapter.service;

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
        log.info("Setting cache for disbursement");
        try {
            setObject(Constants.REDIS_KEY.replace("{uuid}", disbursement.getId()), disbursement);
        } catch (Exception e) {
            // Handle exception, log error, or perform any other necessary action
            e.printStackTrace();
        }
    }

    public Disbursement getDisbursementFromCache(String id) {
        log.info("Getting cache for disbursement");
        try {
            Object disburse = getObject(Constants.REDIS_KEY.replace("{uuid}", id));
            if (disburse != null) {
                return objectMapper.convertValue(disburse, Disbursement.class);
            }
        } catch (Exception e) {
            // Handle exception, log error, or perform any other necessary action
            e.printStackTrace();
        }
        return null; // Return null if cache retrieval fails
    }
}

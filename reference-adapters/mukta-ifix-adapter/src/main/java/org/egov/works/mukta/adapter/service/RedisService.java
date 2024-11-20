package org.egov.works.mukta.adapter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.Constants;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.services.common.models.expense.Payment;
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

    public void setCacheForPayment(Payment payment){
        try {
            String paymentJson = objectMapper.writeValueAsString(payment);
            redisTemplate.opsForValue().set(getPaymentRedisKey(payment.getId()), paymentJson);
        } catch (Exception e) {
            // Handle JSON processing exception
            log.error("Error while pushing data to redis", e);
            throw new CustomException("REDIS_ERROR", "Error while pushing data to redis");
        }
    }

    public Payment getPaymentFromCache(String id) {
        String paymentJson = null;
        try {
            paymentJson = (String) redisTemplate.opsForValue().get(getPaymentRedisKey(id));
        } catch (Exception e) {
            throw  new CustomException("REDIS_ERROR", "Error while fetching data from redis");
        }
        if (paymentJson != null) {
            try {
                return objectMapper.readValue(paymentJson, Payment.class);
            } catch (JsonProcessingException e) {
                // Handle JSON processing exception
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getPaymentRedisKey(String id) {
        return Constants.PAYMENT_REDIS_KEY.replace("{uuid}", id);
    }

    public void setCacheForDisbursement(Disbursement disbursement) {
        try {
            String disbursementJson = objectMapper.writeValueAsString(disbursement);
            redisTemplate.opsForValue().set(getDisburseRedisKey(disbursement.getId()), disbursementJson);
        } catch (JsonProcessingException e) {
            // Handle JSON processing exception
            log.error("Error while pushing data to redis", e);
            throw new CustomException("REDIS_ERROR", "Error while pushing data to redis");
        }
    }

    public Disbursement getDisbursementFromCache(String id) {
        String disbursementJson = null;
        try {
            disbursementJson = (String) redisTemplate.opsForValue().get(getDisburseRedisKey(id));
        } catch (Exception e) {
            throw  new CustomException("REDIS_ERROR", "Error while fetching data from redis");
        }
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

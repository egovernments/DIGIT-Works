package org.egov.digit.expense.calculator.service;

import org.egov.tracer.model.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static org.egov.digit.expense.calculator.util.BillReportConstraints.REPORT_BILL_GEN_REDIS_KEY;

@Service
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setCacheForBillReport(String id){
        try {
            redisTemplate.opsForValue().set(getReportRedisKey(id), true);
        } catch (Exception e) {
            log.error("Error while pushing data to redis", e);
            throw new CustomException("REDIS_ERROR", "Error while pushing data to redis");
        }
    }

    public Boolean isBillIdPresentInCache(String id) {
        return redisTemplate.hasKey(getReportRedisKey(id));
    }

    private String getReportRedisKey(String id) {
        return REPORT_BILL_GEN_REDIS_KEY.replace("{billId}", id);
    }
}
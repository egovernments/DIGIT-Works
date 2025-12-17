package org.egov.works.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.tracer.model.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import static org.egov.works.config.ServiceConstants.*;

@Service
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setCacheForJob(String JobId){
        try {
            redisTemplate.opsForValue().set(getJobRedisKey(JobId), true);
        } catch (Exception e) {
            log.error("Error while pushing data to redis", e);
            throw new CustomException("REDIS_ERROR", "Error while pushing data to redis");
        }
    }

    public Boolean isJobPresentInCache(String jobId) {
        return redisTemplate.hasKey(getJobRedisKey(jobId));
    }

    private String getJobRedisKey(String id) {
        return JOB_REDIS_KEY.replace("{jobId}", id);
    }
}
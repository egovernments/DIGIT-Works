package org.egov.works.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.ServiceCallException;
import org.egov.works.repository.queryBuilder.SchedulerQueryBuilder;
import org.egov.works.repository.rowMapper.SchedulerRowMapper;
import org.egov.works.web.models.JobSchedulerSearchCriteria;
import org.egov.works.web.models.ScheduledJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.egov.works.config.ServiceConstants.EXTERNAL_SERVICE_EXCEPTION;
import static org.egov.works.config.ServiceConstants.SEARCHER_SERVICE_EXCEPTION;

@Repository
@Slf4j
public class ServiceRequestRepository {

    private ObjectMapper mapper;

    private RestTemplate restTemplate;

    private SchedulerQueryBuilder schedulerQueryBuilder;

    private final JdbcTemplate jdbcTemplate;
    private final SchedulerRowMapper schedulerRowMapper;


    @Autowired
    public ServiceRequestRepository(ObjectMapper mapper, RestTemplate restTemplate, SchedulerQueryBuilder schedulerQueryBuilder, JdbcTemplate jdbcTemplate, SchedulerRowMapper schedulerRowMapper) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.schedulerQueryBuilder = schedulerQueryBuilder;
        this.jdbcTemplate = jdbcTemplate;
        this.schedulerRowMapper = schedulerRowMapper;
    }


    public Object fetchResult(StringBuilder uri, Object request) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Object response = null;
        try {
            response = restTemplate.postForObject(uri.toString(), request, Map.class);
        } catch (HttpClientErrorException e) {
            log.error(EXTERNAL_SERVICE_EXCEPTION, e);
            throw new ServiceCallException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(SEARCHER_SERVICE_EXCEPTION, e);
        }

        return response;
    }

    public List<ScheduledJob> getScheduledJobs(JobSchedulerSearchCriteria jobSchedulerSearchCriteria) {
        log.info("Fetching Scheduled Jobs");
        List<Object> preparedStmtList = new ArrayList<>();
        String query = schedulerQueryBuilder.getJobSchedulerSearchQuery(jobSchedulerSearchCriteria, preparedStmtList);
        return jdbcTemplate.query(query, preparedStmtList.toArray(), schedulerRowMapper);
    }
}
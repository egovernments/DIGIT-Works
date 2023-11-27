package org.egov.works.measurement.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.ServiceCallException;
import org.egov.works.measurement.repository.rowmapper.MeasurementServiceRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.egov.works.measurement.config.ServiceConstants.EXTERNAL_SERVICE_EXCEPTION;
import static org.egov.works.measurement.config.ServiceConstants.SEARCHER_SERVICE_EXCEPTION;

@Repository
@Slf4j
public class ServiceRequestRepository {

    private ObjectMapper mapper;

    private RestTemplate restTemplate;

    private String getMeasurementServiceSql = "SELECT * FROM eg_mbs_measurements WHERE mbNumber IN (:mbNumbers)";


    @Autowired
    public ServiceRequestRepository(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
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

    public List<org.egov.works.measurement.web.models.MeasurementService> getMeasurementServicesFromMBSTable(NamedParameterJdbcTemplate jdbcTemplate, List<String> mbNumbers) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mbNumbers", mbNumbers);
        if(mbNumbers.isEmpty()){
            return Collections.emptyList();
        }

        try {
            return jdbcTemplate.query(getMeasurementServiceSql, params, new MeasurementServiceRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList(); // No MeasurementServices found
        }
    }
}
package org.egov.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.ServiceCallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Repository
@Slf4j
public class ServiceRequestRepository {

    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;

    @Autowired
    public ServiceRequestRepository(@Qualifier("objectMapper") ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }


    public Object fetchResult(StringBuilder uri, Object request) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Object response = null;
        try {
            response = restTemplate.postForObject(uri.toString(), request, Map.class);
        } catch (HttpClientErrorException e) {
            log.error("External Service threw an Exception: ", e);
            throw new ServiceCallException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Exception while fetching from searcher: ", e);
        }

        return response;
    }
}

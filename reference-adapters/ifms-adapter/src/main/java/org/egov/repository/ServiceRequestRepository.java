package org.egov.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.ServiceCallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Repository
@Slf4j
public class ServiceRequestRepository {

    private ObjectMapper mapper;
    private RestTemplate restTemplate;

    @Autowired
    public ServiceRequestRepository(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    public Object fetchResult(StringBuilder uri, Object request) {

        Object response = null;
        try {
            response = restTemplate.postForObject(uri.toString(), request, Map.class);
        } catch (HttpClientErrorException e) {
            log.error("External Service threw an Exception: ", e);
            throw new ServiceCallException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Exception while fetching from searcher: ", e.getMessage());
        }

        return response;
    }

    public Object fetchResultWithHeader(StringBuilder uri, Object request, HttpHeaders headers) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Object response = null;
        // Create the HttpEntity with headers and payload
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);
        try {
            response = restTemplate.postForObject(uri.toString(), requestEntity, Map.class);
        } catch (HttpClientErrorException e) {
            log.error("External Service threw an Exception: ", e);
            throw new ServiceCallException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Exception while fetching from searcher: ", e);
            throw new RuntimeException(e);
        }

        return response;
    }

    public String fetchResultString(StringBuilder uri, Object request, HttpHeaders headers) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String response = null;
        // Create the HttpEntity with headers and payload
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);
        try {
            response = restTemplate.postForObject(uri.toString(), requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            log.error("External Service threw an Exception: ", e);
            throw new ServiceCallException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Exception while fetching from searcher: ", e);
            throw new RuntimeException(e);
        }

        return response;
    }

}

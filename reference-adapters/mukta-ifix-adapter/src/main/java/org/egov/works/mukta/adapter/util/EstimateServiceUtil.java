package org.egov.works.mukta.adapter.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.tracer.model.ServiceCallException;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.repository.ServiceRequestRepository;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class EstimateServiceUtil {


    private final MuktaAdaptorConfig config;

    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;

    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public EstimateServiceUtil(MuktaAdaptorConfig config, ObjectMapper mapper, RestTemplate restTemplate, ServiceRequestRepository serviceRequestRepository) {
        this.config = config;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    private StringBuilder getSearchURLWithParams(String tenantId, Set<String> estimateIds) {
        StringBuilder url = new StringBuilder(config.getEstimateHost());
        url.append(config.getEstimateSearchEndpoint());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&ids=");
        url.append(String.join(", ", estimateIds));
        return url;
    }

    public List<Estimate> fetchResult(StringBuilder uri, Object request) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        EstimateResponse response = null;
        try {
            Object res = restTemplate.postForObject(uri.toString(), request, Object.class);
            response = mapper.convertValue(res, EstimateResponse.class);
        } catch (HttpClientErrorException e) {
            log.error("External Service threw an Exception: ", e);
            throw new ServiceCallException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Exception while fetching from searcher: ", e);
            throw new ServiceCallException(e.getMessage());
        }

        return response.getEstimates();
    }

    public String getProjectId(Object userRequest, StringBuilder uri) {
        try {
            LinkedHashMap responseMap = (LinkedHashMap) serviceRequestRepository.fetchResult(uri, userRequest);
            JsonNode jsonNode = mapper.convertValue(responseMap, JsonNode.class);
            return jsonNode.at("/estimates/0/projectId").asText();
        } catch (IllegalArgumentException e) {
            throw new CustomException("IllegalArgumentException", "ObjectMapper not able to convertValue in userCall");
        }
    }

    public String getProjectIdFromEstimate(String tenantId, String estimateId, RequestInfo requestInfo) {
        StringBuilder url = getSearchURLWithParams(tenantId, Collections.singleton(estimateId));
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        return getProjectId(requestInfoWrapper, url);
    }
}

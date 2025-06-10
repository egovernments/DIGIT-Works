package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.ServiceCallException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateResponse;
import org.egov.works.web.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EstimateServiceUtil {


    private final ContractServiceConfiguration config;

    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;

    @Autowired
    public EstimateServiceUtil(ContractServiceConfiguration config, ObjectMapper mapper, RestTemplate restTemplate) {
        this.config = config;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    private StringBuilder getSearchURLWithParams(String tenantId, Set<String> estimateIds) {
        StringBuilder url = new StringBuilder(config.getEstimateHost());
        url.append(config.getEstimateEndpoint());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&ids=");
        url.append(String.join(", ", estimateIds));
        return url;
    }

    public List<Estimate> fetchActiveEstimates(RequestInfo requestInfo, String tenantId, Set<String> estimateIds){
        StringBuilder url = getSearchURLWithParams(tenantId, estimateIds);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        List<Estimate> fetchEstimates = fetchResult(url, requestInfoWrapper);
        return fetchEstimates.stream()
                .filter(e -> Objects.equals(e.getStatus().toString(), Status.ACTIVE.toString()))
                .toList();
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
}

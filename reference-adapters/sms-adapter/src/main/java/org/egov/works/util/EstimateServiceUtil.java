package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.ServiceCallException;
import org.egov.works.config.NotificationServiceConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.egov.works.models.*;
import org.egov.works.repository.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EstimateServiceUtil {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private NotificationServiceConfiguration config;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;
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
        return fetchEstimates.stream().filter(e -> Status.ACTIVE.equals(e.getStatus())).collect(Collectors.toList());
    }

    public List<Estimate> fetchResult(StringBuilder uri, Object request) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        EstimateResponse response = null;
        try {
            response = restTemplate.postForObject(uri.toString(), request, EstimateResponse.class);
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

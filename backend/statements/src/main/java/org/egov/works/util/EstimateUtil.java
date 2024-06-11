package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.ServiceCallException;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.web.models.EstimateResponse;
import org.egov.works.web.models.StatementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class EstimateUtil {

private final StatementConfiguration statementConfiguration;
private final RestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;


@Autowired
    public EstimateUtil(StatementConfiguration statementConfiguration,RestTemplate restTemplate) {
        this.statementConfiguration = statementConfiguration;
        this.restTemplate=restTemplate;
    }


    public Boolean isValidEstimate( StatementRequest statementRequest , RequestInfo requestInfo){

        EstimateResponse estimateResponse=getEstimate(statementRequest.getId(), statementRequest.getTenantId(),requestInfo);
        return (estimateResponse!=null &&!estimateResponse.getEstimates().isEmpty());
    }
    private StringBuilder getSearchURLWithParams(String tenantId, Set<String> estimateIds) {
        StringBuilder url = new StringBuilder(statementConfiguration.getEstimateHost());
        url.append(statementConfiguration.getEstimateSearchEndpoint());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&ids=");
        url.append(String.join(", ", estimateIds));
        return url;
    }

    public EstimateResponse getEstimate(String estimateId, String tenantId, RequestInfo requestInfo) {
    log.info("EstimateUtil::getEstimate");
    Set<String> estimateIdSet= new HashSet<>();
        estimateIdSet.add(estimateId);
        StringBuilder url = getSearchURLWithParams(tenantId, estimateIdSet);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        EstimateResponse fetchEstimates = fetchResult(url, requestInfoWrapper);
//        if(response.getResponseInfo().getStatus().equals(HttpStatus.OK)){
//
//        }

        return fetchEstimates;
    }
    public EstimateResponse fetchResult(StringBuilder uri, Object request) {
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

        return response;
    }
}


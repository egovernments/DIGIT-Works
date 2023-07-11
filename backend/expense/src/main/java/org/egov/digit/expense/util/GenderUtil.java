package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.digit.expense.web.models.IndividualSearch;
import org.egov.digit.expense.web.models.IndividualSearchRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.digit.expense.config.Constants.*;

@Component
@Slf4j
public class GenderUtil {
    @Autowired
    private Configuration config;

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ObjectMapper mapper;

    private Map<String,String> fetchGenderDetails(RequestInfo requestInfo, String tenantId, List<String> ids)  {
        String uri = getIndividualSearchURLWithParams(tenantId).toUriString();

        IndividualSearch individualSearch = IndividualSearch.builder().id(ids).build();
        IndividualSearchRequest individualSearchRequest = IndividualSearchRequest.builder()
                .requestInfo(requestInfo).individual(individualSearch).build();

        log.info("Individual search request -> {}", individualSearchRequest);
        Object individualRes =  restRepo.fetchResult(new StringBuilder(uri), individualSearchRequest);

        Map<String, String> individualDetails = new HashMap<>();
        List<String> individualGender = null;

        try{
            individualGender = JsonPath.read(individualRes, INDIVIDUAL_GENDER_PATH);

        } catch (Exception e){
            throw new CustomException("PARSING_INDIVIDUAL_ERROR", "Failed to parse response from individual");
        }

        if(!individualGender.isEmpty()) {
            individualDetails.put(GENDER, individualGender.get(0));
        }else{
            log.info("The Payee is not available in the individual index, Ids : ", ids.get(0));
        }

        return individualDetails;
    }

    private UriComponentsBuilder getIndividualSearchURLWithParams(String tenantId) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getIndividualServiceHost()).append(config.getIndividualServiceEndpoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("limit", 100)
                .queryParam("offset", 0)
                .queryParam("tenantId", tenantId);

        return uriBuilder;
    }

    public Map<String, String> getGenderDetails(RequestInfo requestInfo, String tenantId, List<String> ids){
        Map<String, String> GenderDetails = fetchGenderDetails(requestInfo, tenantId, ids);
        return GenderDetails;
    }
}

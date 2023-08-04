package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
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

    private String fetchGenderDetails(RequestInfo requestInfo, String tenantId, String identifier)  {
        String uri = getIndividualSearchURLWithParams(tenantId).toUriString();

        Object individualSearchRequest = getIndividualSearchRequest(requestInfo,identifier);
        Object individualRes =  restRepo.fetchResult(new StringBuilder(uri), individualSearchRequest);

        String gender = "";
        List<String> individualGender = null;

        try{
            individualGender = JsonPath.read(individualRes, INDIVIDUAL_GENDER_PATH);

        } catch (Exception e){
            throw new CustomException("PARSING_INDIVIDUAL_ERROR", "Failed to parse response from individual");
        }

        if(!individualGender.isEmpty()) {
            gender= individualGender.get(0);
        }else{
            log.info("The Payee is not available in the individual index, Ids : ", identifier);
        }

        return gender;
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

    private Object getIndividualSearchRequest(RequestInfo requestInfo, String Identifier){
        ObjectNode individualSearchRequestNode = mapper.createObjectNode();
        ObjectNode individualNode = mapper.createObjectNode();

        ArrayNode Ids = mapper.createArrayNode();

        Ids.add(Identifier);
        individualNode.putPOJO(ID,Ids);

        individualSearchRequestNode.putPOJO(REQUEST_INFO,requestInfo);
        individualSearchRequestNode.putPOJO(INDIVIDUAL,individualNode);

        return individualSearchRequestNode;
    }
    public  String getGenderDetails(RequestInfo requestInfo, String tenantId, String identifier){
        String Gender = "";
        try{
            Gender = fetchGenderDetails(requestInfo, tenantId, identifier);
        }catch (Exception e){
            log.info("The Exception occured in fetching gender of payee: ",e);
        }
        return Gender;
    }
}

package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.digit.expense.web.models.IndividualDetails;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.egov.digit.expense.config.Constants.*;

@Component
@Slf4j
public class IndividualUtil {
    private final Configuration config;

    private final ServiceRequestRepository serviceRequestRepository;

    private final ObjectMapper mapper;

    @Autowired
    public IndividualUtil(Configuration config, ServiceRequestRepository serviceRequestRepository, ObjectMapper mapper) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
        this.mapper = mapper;
    }

    private IndividualDetails fetchIndividualDetails(RequestInfo requestInfo, String tenantId, String identifier)  {
        String uri = getIndividualSearchURLWithParams(tenantId).toUriString();

        Object individualSearchRequest = getIndividualSearchRequest(requestInfo,identifier);
        Object individualResponse =  serviceRequestRepository.fetchResult(new StringBuilder(uri), individualSearchRequest);

        return IndividualDetails.builder()
                .name(getIndividualField(individualResponse, INDIVIDUAL_NAME_PATH, identifier))
                .phoneNumber(getIndividualField(individualResponse, INDIVIDUAL_PHONE_NUMBER_PATH, identifier))
                .email(getIndividualField(individualResponse, INDIVIDUAL_EMAIL_PATH, identifier))
                .role(getIndividualField(individualResponse, INDIVIDUAL_ROLE_PATH, identifier))
                .build();

    }

    private String getIndividualField(Object individualResponse, String fieldPath, String identifier){
        String field = "";
        List<String> individualField = null;

        try{
            individualField = JsonPath.read(individualResponse, fieldPath);

        } catch (Exception e){
            throw new CustomException("PARSING_INDIVIDUAL_ERROR", "Failed to parse response from individual");
        }

        if(!individualField.isEmpty()) {
            field = individualField.get(0);
        }else{
            log.info("The Payee is not available in the individual index, Ids : ", identifier);
        }

        return field;

    }

    private UriComponentsBuilder getIndividualSearchURLWithParams(String tenantId) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getIndividualServiceHost()).append(config.getIndividualServiceEndpoint());

        return UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("limit", 100)
                .queryParam("offset", 0)
                .queryParam("tenantId", tenantId);
    }

    private Object getIndividualSearchRequest(RequestInfo requestInfo, String identifier){
        ObjectNode individualSearchRequestNode = mapper.createObjectNode();
        ObjectNode individualNode = mapper.createObjectNode();

        ArrayNode ids = mapper.createArrayNode();

        ids.add(identifier);
        individualNode.putPOJO(ID,ids);

        individualSearchRequestNode.putPOJO(REQUEST_INFO,requestInfo);
        individualSearchRequestNode.putPOJO(INDIVIDUAL,individualNode);

        return individualSearchRequestNode;
    }
    public IndividualDetails getIndividualDetails(RequestInfo requestInfo, String tenantId, String identifier){
        IndividualDetails individualDetails = null;
        try{
            individualDetails = fetchIndividualDetails(requestInfo, tenantId, identifier);
        }catch (Exception e){
            log.info("The Exception occured in fetching details of payee: ",e);
        }
        return individualDetails;
    }

    /**
     * Searches individuals by role codes and returns those with a non-blank email.
     * Uses the same Individual search endpoint, passing roleCodes in the request body.
     */
    public List<IndividualDetails> searchByRoleCodes(RequestInfo requestInfo, String tenantId, List<String> roleCodes) {
        List<IndividualDetails> result = new ArrayList<>();
        try {
            String uri = getIndividualSearchURLWithParams(tenantId)
                    .queryParam("limit", 1000)
                    .toUriString();

            ObjectNode body = mapper.createObjectNode();
            body.putPOJO(REQUEST_INFO, requestInfo);
            ObjectNode individualNode = mapper.createObjectNode();
            individualNode.putPOJO("roleCodes", roleCodes);
            body.set(INDIVIDUAL, individualNode);

            Object response = serviceRequestRepository.fetchResult(new StringBuilder(uri), body);

            List<String> ids    = JsonPath.read(response, "$.Individual[*].id");
            List<String> emails = JsonPath.read(response, "$.Individual[*].email");
            List<String> names  = JsonPath.read(response, "$.Individual[*].name.givenName");

            for (int i = 0; i < ids.size(); i++) {
                String email = i < emails.size() ? emails.get(i) : null;
                if (email == null || email.isBlank()) continue;
                result.add(IndividualDetails.builder()
                        .id(ids.get(i))
                        .email(email)
                        .name(i < names.size() ? names.get(i) : null)
                        .build());
            }
        } catch (Exception e) {
            log.warn("IndividualUtil: failed to search individuals by roleCodes={}: {}", roleCodes, e.getMessage());
        }
        return result;
    }
}

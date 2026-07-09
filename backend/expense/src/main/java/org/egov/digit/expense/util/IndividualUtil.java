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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private IndividualDetails fetchIndividualDetails(RequestInfo requestInfo, String tenantId, String identifier) {
        String uri = getIndividualSearchURLWithParams(tenantId).toUriString();
        Object individualSearchRequest = getIndividualSearchRequest(requestInfo, identifier);
        Object individualResponse = serviceRequestRepository.fetchResult(new StringBuilder(uri), individualSearchRequest);

        List<IndividualDetails> details = mapIndividualResponse(individualResponse);
        if (details.isEmpty()) {
            log.info("The Payee is not available in the individual index, Id: {}", identifier);
            return null;
        }
        return details.get(0);
    }

    private List<IndividualDetails> mapIndividualResponse(Object response) {
        List<IndividualDetails> result = new ArrayList<>();
        List<Map<String, Object>> individuals = JsonPath.read(response, "$.Individual");
        for (Map<String, Object> individual : individuals) {
            result.add(mapSingleIndividual(individual));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private IndividualDetails mapSingleIndividual(Map<String, Object> individual) {
        String id = (String) individual.get("id");
        String userUuid = (String) individual.get("userUuid");
        String email = (String) individual.get("email");
        String phoneNumber = (String) individual.get("mobileNumber");

        Map<String, Object> nameMap = (Map<String, Object>) individual.get("name");
        String name = nameMap != null ? (String) nameMap.get("givenName") : null;

        List<Map<String, Object>> skills = (List<Map<String, Object>>) individual.get("skills");
        List<String> roles = new ArrayList<>();
        if (skills != null) {
            for (Map<String, Object> skill : skills) {
                String type = (String) skill.get("type");
                if (type != null) roles.add(type);
            }
        }

        return IndividualDetails.builder()
                .id(id)
                .userUuid(userUuid)
                .email(email)
                .phoneNumber(phoneNumber)
                .name(name)
                .roles(roles)
                .build();
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

            List<IndividualDetails> all = mapIndividualResponse(response);
            for (IndividualDetails details : all) {
                if (details.getEmail() != null && !details.getEmail().isBlank()) {
                    result.add(details);
                }
            }
        } catch (Exception e) {
            log.warn("IndividualUtil: failed to search individuals by roleCodes={}: {}", roleCodes, e.getMessage());
            throw e;
        }
        return result;
    }
}

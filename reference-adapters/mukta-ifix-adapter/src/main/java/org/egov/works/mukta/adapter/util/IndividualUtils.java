package org.egov.works.mukta.adapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.IndividualBulkResponse;
import org.egov.common.models.individual.IndividualSearch;
import org.egov.common.models.individual.IndividualSearchRequest;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.constants.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.validation.Valid;
import java.util.*;

@Component
@Slf4j
public class IndividualUtils {
    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final MuktaAdaptorConfig config;

    @Autowired
    public IndividualUtils(RestTemplate restTemplate, ObjectMapper mapper, MuktaAdaptorConfig config) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.config = config;
    }

    public @Valid List<Individual> getIndividualById(RequestInfo requestInfo, List<String> ids, String tenantId) {
        IndividualSearch individualSearch = null;
        if (requestInfo != null && ids != null && !ids.isEmpty()) {
            Set<String> uniqueIndividualIds = new HashSet<>(ids);
            individualSearch = IndividualSearch.builder()
                    .id(new ArrayList<>(uniqueIndividualIds))
                    .build();
        } else {
            throw new CustomException(Error.INVALID_INDIVIDUAL_SEARCH_CRITERIA, Error.INVALID_INDIVIDUAL_SEARCH_CRITERIA_MESSAGE);
        }

        IndividualSearchRequest individualSearchRequest = IndividualSearchRequest.builder()
                .requestInfo(requestInfo)
                .individual(individualSearch)
                .build();

        return getIndividuals(individualSearchRequest, tenantId, 0, individualSearch.getId().size());
    }

    public @Valid List<Individual> getIndividuals(Object individualRequest, String tenantId, Integer offset, Integer limit) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getIndividualHost()).append(config.getIndividualSearchEndPoint());
        uri.append("?tenantId=").append(tenantId);
        uri.append("&offset=").append(offset);
        uri.append("&limit=").append(limit);
        Object response = new HashMap<>();
        IndividualBulkResponse individualBulkResponse = new IndividualBulkResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), individualRequest, Map.class);
            individualBulkResponse = mapper.convertValue(response, IndividualBulkResponse.class);
            log.info("Fetched data from individual service.");
        } catch (Exception e) {
            log.error("Exception occurred while fetching individuals lists from individual service: ", e);
        }

        return individualBulkResponse.getIndividual();
    }

}

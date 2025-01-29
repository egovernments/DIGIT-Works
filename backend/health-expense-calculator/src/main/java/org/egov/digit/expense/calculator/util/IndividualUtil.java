package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.IndividualBulkResponse;
import org.egov.common.models.individual.IndividualSearch;
import org.egov.common.models.individual.IndividualSearchRequest;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@Slf4j
public class IndividualUtil {

    private final ExpenseCalculatorConfiguration config;

    private final ServiceRequestRepository requestRepository;
    private final ObjectMapper mapper;

    @Autowired
    public IndividualUtil(ExpenseCalculatorConfiguration config, ServiceRequestRepository requestRepository, ObjectMapper mapper) {
        this.config = config;
        this.requestRepository = requestRepository;
        this.mapper = mapper;
    }

    /**
     * fetch the individual details from individual service
     *
     * @param ids
     * @param requestInfo
     * @param tenantId
     */
    public List<Individual> fetchIndividualDetails(List<String> ids, RequestInfo requestInfo, String tenantId) {
        String uri = getSearchURLWithParams(tenantId, ids.size()).toUriString();

        IndividualSearch individualSearch = IndividualSearch.builder().id(ids).build();
        IndividualSearchRequest individualSearchRequest = IndividualSearchRequest.builder()
                .requestInfo(requestInfo).individual(individualSearch).build();

        log.info("IndividualUtil::fetchIndividualDetails::call individual search with tenantId::" + tenantId + "::individual ids::" + ids);

        Object response = requestRepository.fetchResult(new StringBuilder(uri), individualSearchRequest);

        IndividualBulkResponse individualBulkResponse = mapper.convertValue(response, IndividualBulkResponse.class);


        return individualBulkResponse.getIndividual();
    }

    private UriComponentsBuilder getSearchURLWithParams(String tenantId, int limit) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getIndividualHost()).append(config.getIndividualSearchEndPoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("limit", limit)
                .queryParam("offset", 0)
                .queryParam("tenantId", tenantId);

        return uriBuilder;
    }
}


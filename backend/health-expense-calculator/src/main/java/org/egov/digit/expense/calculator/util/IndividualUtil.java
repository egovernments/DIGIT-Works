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
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
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

    /**
     * Fetch individual details from user ID.
     * Used for permission validation - converts user UUID to individual ID.
     *
     * @param userId User ID (UUID from UserInfo)
     * @param requestInfo Request info
     * @param tenantId Tenant ID
     * @return List of individuals (typically single individual)
     */
    public List<Individual> getIndividualDetailsFromUserId(Long userId, RequestInfo requestInfo, String tenantId) {
        String uri = getSearchURLWithParams(tenantId, 1).toUriString();
        List<Long> userIds = Collections.singletonList(userId);

        IndividualSearch individualSearch = IndividualSearch.builder().userId(userIds).build();
        IndividualSearchRequest individualSearchRequest = IndividualSearchRequest.builder()
                .requestInfo(requestInfo).individual(individualSearch).build();

        log.info("IndividualUtil::getIndividualDetailsFromUserId::Fetching individual for userId: {} tenantId: {}", userId, tenantId);

        Object response = requestRepository.fetchResult(new StringBuilder(uri), individualSearchRequest);

        if (response == null) {
            log.error("IndividualUtil::getIndividualDetailsFromUserId::Individual service returned null response for userId: {}", userId);
            throw new CustomException("INDIVIDUAL_SEARCH_FAILED",
                "Failed to fetch individual details for user ID: " + userId);
        }

        IndividualBulkResponse individualBulkResponse = mapper.convertValue(response, IndividualBulkResponse.class);

        if (individualBulkResponse == null || CollectionUtils.isEmpty(individualBulkResponse.getIndividual())) {
            log.error("IndividualUtil::getIndividualDetailsFromUserId::Individual not found for userId: {}", userId);
            throw new CustomException("INDIVIDUAL_NOT_FOUND",
                "Individual not found for user ID: " + userId);
        }

        log.info("IndividualUtil::getIndividualDetailsFromUserId::Successfully fetched individual: {} for userId: {}",
            individualBulkResponse.getIndividual().get(0).getId(), userId);

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


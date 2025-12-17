package org.egov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.IndividualBulkResponse;
import org.egov.common.models.individual.IndividualSearch;
import org.egov.common.models.individual.IndividualSearchRequest;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

@Component
@Slf4j
public class IndividualServiceUtil {

    private final ServiceRequestRepository serviceRequestRepository;

    private final AttendanceServiceConfiguration config;

    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;
    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public IndividualServiceUtil(ServiceRequestRepository serviceRequestRepository, AttendanceServiceConfiguration config, @Qualifier("objectMapper") ObjectMapper mapper, RestTemplate restTemplate, MultiStateInstanceUtil multiStateInstanceUtil) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.config = config;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    public List<String> fetchIndividualIds(List<String> individualIds, RequestInfo requestInfo, String tenantId) {

        List<Individual> individualList = getIndividualDetails(individualIds, requestInfo, tenantId);

        List<String> ids = null;
        try {
            ids = individualList.stream().map(Individual::getId).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse Individual service response");
        }
        log.info("Individual search fetched successfully");
        return ids;
    }

    public List<Individual> getIndividualDetails(List<String> individualIds, RequestInfo requestInfo, String tenantId) {

        String uri = getSearchURLWithParams(tenantId).toUriString();

        IndividualSearch individualSearch = IndividualSearch.builder().id(individualIds).build();
        IndividualSearchRequest individualSearchRequest = IndividualSearchRequest.builder()
                .requestInfo(requestInfo).individual(individualSearch).build();

        IndividualBulkResponse response = null;
        log.info("call individual search with tenantId::" + tenantId + "::individual ids::" + individualIds);

        try {
            response = restTemplate.postForObject(uri, individualSearchRequest, IndividualBulkResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("Error thrown from individual search service::" + httpClientOrServerExc.getStatusCode());
            throw new CustomException("INDIVIDUAL_SEARCH_SERVICE_EXCEPTION", "Error thrown from individual search service::" + httpClientOrServerExc.getStatusCode());
        }
        if (response == null || CollectionUtils.isEmpty(response.getIndividual())) {
            throw new CustomException("INDIVIDUAL_SEARCH_RESPONSE_IS_EMPTY", "Individuals not found");
        }

        return response.getIndividual();
    }

    private UriComponentsBuilder getSearchURLWithParams(String tenantId) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getIndividualHost()).append(config.getIndividualSearchEndpoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("limit", config.getAttendanceRegisterDefaultLimit())
                .queryParam("offset", config.getAttendanceRegisterDefaultOffset())
                .queryParam("tenantId", tenantId);

        return uriBuilder;
    }

    public List<Individual> getIndividualDetailsFromUserId(Long userId, RequestInfo requestInfo, String tenantId) {
        String uri = getSearchURLWithParams(multiStateInstanceUtil.getStateLevelTenant(tenantId)).toUriString();
        List<Long> userIds = Collections.singletonList(userId);
        IndividualSearch individualSearch = IndividualSearch.builder().userId(userIds).build();
        IndividualSearchRequest individualSearchRequest = IndividualSearchRequest.builder()
                .requestInfo(requestInfo).individual(individualSearch).build();

        IndividualBulkResponse response = null;
        log.info("call individual search with tenantId::" + tenantId + "::user id::" + userId);

        try {
            response = restTemplate.postForObject(uri, individualSearchRequest, IndividualBulkResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("Error thrown from individual search service::" + httpClientOrServerExc.getStatusCode());
            throw new CustomException("INDIVIDUAL_SEARCH_SERVICE_EXCEPTION", "Error thrown from individual search service::" + httpClientOrServerExc.getStatusCode());
        }
        if (response == null || CollectionUtils.isEmpty(response.getIndividual())) {
            throw new CustomException("INDIVIDUAL_SEARCH_RESPONSE_IS_EMPTY", "Individuals not found");
        }

        return response.getIndividual();
    }

    /**
     * Retrieves individual details based on the provided search criteria and request information.
     *
     * @param individualSearch The search criteria for retrieving individual details
     * @param requestInfo      The request information
     * @param tenantId         The ID of the tenant
     * @return A list of individual details matching the search criteria
     */
    public List<Individual> getIndividualDetailsFromSearchCriteria(IndividualSearch individualSearch, RequestInfo requestInfo, String tenantId) {
        String uri = getSearchURLWithParams(multiStateInstanceUtil.getStateLevelTenant(tenantId)).toUriString();
        IndividualSearchRequest individualSearchRequest = IndividualSearchRequest.builder()
                .requestInfo(requestInfo).individual(individualSearch).build();

        IndividualBulkResponse response = null;
        log.info("call individual search with tenantId::" + tenantId + "::indidividual search criteria::" + individualSearch.toString());

        try {
            response = restTemplate.postForObject(uri, individualSearchRequest, IndividualBulkResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("Error thrown from individual search service::" + httpClientOrServerExc.getStatusCode());
            throw new CustomException("INDIVIDUAL_SEARCH_SERVICE_EXCEPTION", "Error thrown from individual search service::" + httpClientOrServerExc.getStatusCode());
        }
        if (response == null || CollectionUtils.isEmpty(response.getIndividual())) {
            throw new CustomException("INDIVIDUAL_SEARCH_RESPONSE_IS_EMPTY", "Individuals not found");
        }

        return response.getIndividual();
    }
}

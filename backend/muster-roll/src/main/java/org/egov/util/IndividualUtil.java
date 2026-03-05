package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.IndividualBulkResponse;
import org.egov.common.models.individual.IndividualSearch;
import org.egov.common.models.individual.IndividualSearchRequest;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class IndividualUtil {

    private final RestTemplate restTemplate;
    private final MusterRollServiceConfiguration config;

    @Autowired
    public IndividualUtil(RestTemplate restTemplate, MusterRollServiceConfiguration config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public List<Individual> fetchIndividualDetails(List<String> ids, RequestInfo requestInfo, String tenantId) {
        if (CollectionUtils.isEmpty(ids)) return new ArrayList<>();

        List<String> uniqueIds = ids.stream().distinct().toList();

        StringBuilder uri = new StringBuilder();
        uri.append(config.getIndividualHost()).append(config.getIndividualSearchEndpoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("limit", 100)
                .queryParam("offset", 0)
                .queryParam("tenantId", tenantId);

        IndividualSearch individualSearch = IndividualSearch.builder().id(uniqueIds).build();
        IndividualSearchRequest individualSearchRequest = IndividualSearchRequest.builder()
                .requestInfo(requestInfo).individual(individualSearch).build();

        log.info("IndividualUtil::fetchIndividualDetails::call individual search with tenantId::" + tenantId
                + "::individual ids::" + uniqueIds);

        IndividualBulkResponse response = null;
        try {
            response = restTemplate.postForObject(uriBuilder.toUriString(), individualSearchRequest, IndividualBulkResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("IndividualUtil::fetchIndividualDetails::Error thrown from individual search service::" + httpClientOrServerExc.getStatusCode());
            throw new CustomException("INDIVIDUAL_SEARCH_SERVICE_EXCEPTION", "Error thrown from individual search service::" + httpClientOrServerExc.getStatusCode());
        }

        if (response == null || CollectionUtils.isEmpty(response.getIndividual())) {
            log.info("IndividualUtil::fetchIndividualDetails::Individual search returned empty response for tenantId::" + tenantId);
            return new ArrayList<>();
        }

        log.info("IndividualUtil::fetchIndividualDetails::Individual search fetched successfully, count::" + response.getIndividual().size());
        return response.getIndividual();
    }

    public Map<String, Individual> fetchIndividualDetailsAsMap(List<String> ids, RequestInfo requestInfo, String tenantId) {
        List<Individual> individuals = fetchIndividualDetails(ids, requestInfo, tenantId);
        return individuals.stream()
                .collect(Collectors.toMap(Individual::getId, individual -> individual, (i1, i2) -> i1));
    }
}
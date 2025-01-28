package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.IndividualSearch;
import org.egov.common.models.individual.IndividualSearchRequest;
import org.egov.config.Configuration;
import org.egov.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@Slf4j
public class IndividualUtil {

    private final Configuration config;

    private final ServiceRequestRepository requestRepository;

    @Autowired
    public IndividualUtil(Configuration config, ServiceRequestRepository requestRepository) {
        this.config = config;
        this.requestRepository = requestRepository;
    }

    /**
     * fetch the individual details from individual service
     *
     * @param ids
     * @param requestInfo
     * @param tenantId
     */
    public Object fetchIndividualDetails(List<String> ids, RequestInfo requestInfo, String tenantId) {
        String uri = getSearchURLWithParams(tenantId).toUriString();

        IndividualSearch individualSearch = IndividualSearch.builder().id(ids).build();
        IndividualSearchRequest individualSearchRequest = IndividualSearchRequest.builder()
                .requestInfo(requestInfo).individual(individualSearch).build();

        log.info("IndividualUtil::fetchIndividualDetails::call individual search with tenantId::" + tenantId + "::individual ids::" + ids);

        Object response = requestRepository.fetchResult(new StringBuilder(uri), individualSearchRequest);

        return response;
    }

    private UriComponentsBuilder getSearchURLWithParams(String tenantId) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getIndividualHost()).append(config.getIndividualSearchEndPoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("limit", 100)
                .queryParam("offset", 0)
                .queryParam("tenantId", tenantId);

        return uriBuilder;
    }
}

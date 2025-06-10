package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.works.services.common.models.organization.OrgSearchCriteria;
import org.egov.works.services.common.models.organization.OrgSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class OrganisationUtil {

    private final Configuration config;

    private final RestTemplate restTemplate;

    private final ServiceRequestRepository requestRepository;

    @Autowired
    public OrganisationUtil(Configuration config, RestTemplate restTemplate, ServiceRequestRepository requestRepository) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.requestRepository = requestRepository;
    }


    /**
     * fetch the organisation details from organisation service
     *
     * @param ids
     * @param requestInfo
     * @param tenantId
     */
    public Object fetchOrganisationDetails(List<String> ids, RequestInfo requestInfo, String tenantId) {
        Object response = null;
        if (!CollectionUtils.isEmpty(ids)) {
            StringBuilder uri = new StringBuilder();
            uri.append(config.getOrganisationHost()).append(config.getOrganisationSearchEndPoint());

            OrgSearchCriteria orgSearchCriteria = OrgSearchCriteria.builder()
                    .id(ids)
                    .tenantId(tenantId)
                    .build();

            OrgSearchRequest orgSearchRequest = OrgSearchRequest.builder()
                    .requestInfo(requestInfo)
                    .searchCriteria(orgSearchCriteria)
                    .build();

            log.info("BankAccountsService::fetchOrganisationDetails::call organisation search with tenantId::" + tenantId + "::organisation ids::" + ids);

            response = requestRepository.fetchResult(new StringBuilder(uri), orgSearchRequest);
        }
        return response;
    }

}

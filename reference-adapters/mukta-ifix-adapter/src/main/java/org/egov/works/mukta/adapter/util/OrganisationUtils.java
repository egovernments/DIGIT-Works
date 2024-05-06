package org.egov.works.mukta.adapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.web.models.Pagination;
import org.egov.works.mukta.adapter.web.models.organisation.OrgResponse;
import org.egov.works.mukta.adapter.web.models.organisation.OrgSearchCriteria;
import org.egov.works.mukta.adapter.web.models.organisation.OrgSearchRequest;
import org.egov.works.mukta.adapter.web.models.organisation.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

@Component
@Slf4j
public class OrganisationUtils {
    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final MuktaAdaptorConfig config;

    @Autowired
    public OrganisationUtils(RestTemplate restTemplate, ObjectMapper mapper, MuktaAdaptorConfig config) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.config = config;
    }

    public @Valid List<Organisation> getOrganisationsById(RequestInfo requestInfo, List<String> ids, String tenantId) {
        log.info("Fetching organisations details.");
        OrgSearchCriteria orgSearchCriteria = null;
        if (requestInfo != null && ids != null && !ids.isEmpty()) {
            Set<String> uniqueIds = new HashSet<>(ids);
            orgSearchCriteria = OrgSearchCriteria.builder()
                    .tenantId(tenantId)
                    .id(new ArrayList<>(uniqueIds))
                    .build();
        } else {
            throw new CustomException(Error.ORG_CRITERIA_ERROR, Error.ORG_CRITERIA_ERROR_MESSAGE);
        }

        Pagination pagination = Pagination.builder()
                .limit(orgSearchCriteria.getId().size())
                .offSet(0)
                .order(Pagination.OrderEnum.ASC)
                .build();

        OrgSearchRequest orgSearchRequest = OrgSearchRequest.builder()
                .requestInfo(requestInfo)
                .searchCriteria(orgSearchCriteria)
                .pagination(pagination)
                .build();

        return getOrganisations(orgSearchRequest);
    }

    public @Valid List<Organisation> getOrganisations(Object bankAccountRequest) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getOrganisationHost()).append(config.getOrganisationSearchEndPoint());
        Object response = new HashMap<>();
        OrgResponse orgResponse = new OrgResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), bankAccountRequest, Map.class);
            orgResponse = mapper.convertValue(response, OrgResponse.class);
            log.info("Organisation details fetched.");
        } catch (Exception e) {
            log.error("Exception occurred while fetching organisation getOrganisationsById:getOrganisations: ", e);
        }

        return orgResponse.getOrganisations();
    }

}

package org.egov.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.*;
import org.egov.web.models.bankaccount.BankAccount;
import org.egov.web.models.bankaccount.BankAccountResponse;
import org.egov.web.models.bankaccount.BankAccountSearchCriteria;
import org.egov.web.models.bankaccount.BankAccountSearchRequest;
import org.egov.web.models.organisation.OrgResponse;
import org.egov.web.models.organisation.OrgSearchCriteria;
import org.egov.web.models.organisation.OrgSearchRequest;
import org.egov.web.models.organisation.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

@Component
@Slf4j
public class OrganisationUtils {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IfmsAdapterConfig config;

	public @Valid List<Organisation> getOrganisationsById(RequestInfo requestInfo, List<String> ids, String tenantId) throws Exception {
		log.info("Fetching organisations details.");
		List<String> billIds = new ArrayList<>();
		OrgSearchCriteria orgSearchCriteria = null;
		if (requestInfo != null && ids != null && !ids.isEmpty()) {
			Set<String> uniqueIds = new HashSet<>(ids);
			orgSearchCriteria = OrgSearchCriteria.builder()
					.tenantId(tenantId)
					.id(new ArrayList<>(uniqueIds))
					.build();
		} else {
			throw new RuntimeException("Request info, or ids are empty in organisation search");
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

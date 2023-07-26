package org.egov.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.IndividualBulkResponse;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.individual.IndividualSearch;
import org.egov.web.models.individual.IndividualSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

@Component
@Slf4j
public class IndividualUtils {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IfmsAdapterConfig config;

	public @Valid List<Individual> getIndividualById(RequestInfo requestInfo, List<String> ids, String tenantId) throws Exception {
		List<String> billIds = new ArrayList<>();
		IndividualSearch individualSearch = null;
		if (requestInfo != null && ids != null && !ids.isEmpty()) {
			Set<String> uniqueIndividualIds = new HashSet<>(ids);
			individualSearch = IndividualSearch.builder()
					.id(new ArrayList<>(uniqueIndividualIds))
					.build();
		} else {
			throw new Exception("");
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
		uri.append("?tenantId="+tenantId);
		uri.append("&offset="+offset);
		uri.append("&limit="+limit);
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

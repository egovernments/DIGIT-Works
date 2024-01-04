package org.egov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Component
@Slf4j
public class ContractUtils {
    private final EstimateServiceConfiguration config;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ObjectMapper objectMapper;

    public static final String REQUEST_INFO = "RequestInfo";
    public static final String TENANT_ID = "tenantId";
    public static final String ESTIMATE_IDS = "estimateIds";

    @Autowired
    public ContractUtils(EstimateServiceConfiguration config, ServiceRequestRepository serviceRequestRepository, ObjectMapper objectMapper) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Get the Contract details using estimate number from contract service
     *
     * @param estimateRequest
     * @return
     */
    public Object getContractDetails(RequestInfo requestInfo, Estimate estimate) {
        log.info("ContractsUtil::getContractDetails");
        String estimateId = estimate.getId();
        String tenantId = estimate.getTenantId();

        StringBuilder uriBuilder = getContractUrl();
        ObjectNode contractSearchRequestNode = objectMapper.createObjectNode();
        contractSearchRequestNode.putPOJO(REQUEST_INFO,requestInfo);
        contractSearchRequestNode.putPOJO(TENANT_ID,tenantId);
        contractSearchRequestNode.putPOJO(ESTIMATE_IDS, Collections.singletonList(estimateId));

        log.info("Contract Utils: Search Request For Contract: "+contractSearchRequestNode.toString());
        return serviceRequestRepository.fetchResult(uriBuilder,contractSearchRequestNode);
    }
    private StringBuilder getContractUrl() {
        StringBuilder uriBuilder = new StringBuilder();
        return (uriBuilder.append(config.getContractHost())
                .append(config.getContractSearchEndpoint()));
    }
}

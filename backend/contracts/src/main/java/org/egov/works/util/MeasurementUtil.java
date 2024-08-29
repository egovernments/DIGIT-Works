package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.ContractRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static org.egov.works.util.ContractServiceConstants.*;
import static org.egov.works.util.ContractServiceConstants.CRITERIA;

@Component
@Slf4j
public class MeasurementUtil {

    private final ServiceRequestRepository restRepo;

    private final ObjectMapper mapper;

    private final ContractServiceConfiguration config;

    @Autowired
    public MeasurementUtil(ServiceRequestRepository restRepo, ObjectMapper mapper, ContractServiceConfiguration config) {
        this.restRepo = restRepo;
        this.mapper = mapper;
        this.config = config;
    }

    public Object getMeasurementDetails(ContractRequest contractRequest) {
        log.info("MeasurementUtils::getMeasurementDetails");

        StringBuilder uriBuilder = getMeasurementUrl();
        ObjectNode measurementSearchRequestNode = mapper.createObjectNode();
        ObjectNode criteria = mapper.createObjectNode();
        criteria.putPOJO(REFERENCE_ID, Collections.singletonList(contractRequest.getContract().getContractNumber()));
        criteria.putPOJO(TENANT_ID,contractRequest.getContract().getTenantId());
        criteria.putPOJO(IS_ACTIVE, true);
        measurementSearchRequestNode.putPOJO(REQUEST_INFO,contractRequest.getRequestInfo());
        measurementSearchRequestNode.putPOJO(CRITERIA,criteria);

        log.info("Measurement Utils: Search Request For Measurement: "+measurementSearchRequestNode.toString());
        return restRepo.fetchResult(uriBuilder,measurementSearchRequestNode);
    }
    private StringBuilder getMeasurementUrl() {
        StringBuilder uriBuilder = new StringBuilder();
        return (uriBuilder.append(config.getMeasurementBookHost())
                .append(config.getMeasurementBookSearchEndpoint()));
    }

}

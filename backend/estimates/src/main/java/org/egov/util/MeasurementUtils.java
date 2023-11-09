package org.egov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class MeasurementUtils {

    @Autowired
    private EstimateServiceConfiguration config;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public static final String REQUEST_INFO = "RequestInfo";
    public static final String TENANT_ID = "tenantId";
    public static final String CRITERIA = "criteria";
    public static final String REFERENCE_ID = "referenceId";

    /**
     * Get the Measurement details using sor id from measurement book service
     *
     * @param request
     * @param contractNumber
     * @return
     */
    public Object getMeasurementDetails(EstimateRequest request,String contractNumber) {
        log.info("MeasurementUtils::getMeasurementDetails");
        RequestInfo requestInfo = request.getRequestInfo();
        String tenantId = request.getEstimate().getTenantId();

        StringBuilder uriBuilder = getMeasurementUrl();
        ObjectNode measurementSearchRequestNode = objectMapper.createObjectNode();
        ObjectNode criteria = objectMapper.createObjectNode();
        criteria.putPOJO(REFERENCE_ID, Collections.singletonList(contractNumber));
        criteria.putPOJO(TENANT_ID,tenantId);
        measurementSearchRequestNode.putPOJO(REQUEST_INFO,requestInfo);
        measurementSearchRequestNode.putPOJO(CRITERIA,criteria);

        log.info("Measurement Utils: Search Request For Measurement: "+measurementSearchRequestNode.toString());
        return serviceRequestRepository.fetchResult(uriBuilder,measurementSearchRequestNode);
    }
    private StringBuilder getMeasurementUrl() {
        StringBuilder uriBuilder = new StringBuilder();
        return (uriBuilder.append(config.getMeasurementBookHost())
                .append(config.getMeasurementBookSearchEndpoint()));
    }
}

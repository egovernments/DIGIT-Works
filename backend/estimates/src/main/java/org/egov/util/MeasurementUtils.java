package org.egov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MeasurementUtils {

    @Autowired
    private EstimateServiceConfiguration config;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private StringBuilder getMeasurementUrl() {
        StringBuilder uriBuilder = new StringBuilder();
        return (uriBuilder.append(config.getMeasurementBookHost())
                .append(config.getMeasurementBookSearchEndpoint()));
    }
}

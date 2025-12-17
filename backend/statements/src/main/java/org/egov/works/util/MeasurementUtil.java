package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.services.common.models.measurement.Measurement;
import org.egov.works.services.common.models.measurement.MeasurementCriteria;
import org.egov.works.services.common.models.measurement.MeasurementResponse;
import org.egov.works.services.common.models.measurement.MeasurementSearchRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MeasurementUtil {

    private final ServiceRequestRepository restRepo;
    private final StatementConfiguration configs;
    private final ObjectMapper mapper;

    public MeasurementUtil(ServiceRequestRepository restRepo, StatementConfiguration configs, ObjectMapper mapper) {
        this.restRepo = restRepo;
        this.configs = configs;
        this.mapper = mapper;
    }

    public List<Measurement> fetchMeasurements(String id, String tenantId, RequestInfo requestInfo) {
        MeasurementSearchRequest measurementSearchRequest = MeasurementSearchRequest.builder()
                .requestInfo(requestInfo)
                .criteria(MeasurementCriteria.builder()
                        .ids(Collections.singletonList(id)).tenantId(tenantId)
                        .build())
                .build();
        StringBuilder url = getMeasurementSearchUrl();

        Object response = restRepo.fetchResult(url, measurementSearchRequest);
        MeasurementResponse measurementResponse = mapper.convertValue(response, MeasurementResponse.class);
        return measurementResponse.getMeasurements();
    }

    private StringBuilder getMeasurementSearchUrl() {
        return new StringBuilder(configs.getMeasurementHost())
                .append(configs.getMeasurementSearchEndpoint());
    }

}

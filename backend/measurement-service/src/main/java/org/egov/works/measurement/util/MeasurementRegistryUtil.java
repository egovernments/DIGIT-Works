package org.egov.works.measurement.util;

import digit.models.coremodels.Document;
import org.apache.commons.lang.StringUtils;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.config.ErrorConfiguration;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.service.MeasurementRegistry;
import org.egov.works.measurement.web.models.*;
import digit.models.coremodels.AuditDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Component
public class MeasurementRegistryUtil {
    @Autowired
    private IdgenUtil idgenUtil;
    @Autowired
    private Configuration configuration;
    @Autowired
    private ErrorConfiguration errorConfigs;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private MeasurementServiceUtil measurementServiceUtil;
    @Autowired
    private MeasurementRegistry measurementRegistryUtil;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${egov.mbRegistry.host}")
    private String mbRegistryHost;
    @Value("${egov.mbRegistry.create.path}")
    private String mbRegistryCreate;
    @Value("${egov.mbRegistry.update.path}")
    private String mbRegistryUpdate;
    @Value("${egov.mbRegistry.search.path}")
    private String mbRegistrySearch;

    public ResponseEntity<MeasurementResponse> createMeasurements(MeasurementServiceRequest body){
        List<Measurement> measurementList = measurementServiceUtil.convertToMeasurementList(body.getMeasurements());
        String url = mbRegistryHost+mbRegistryCreate;
        MeasurementRequest measurementRegistryRequest = MeasurementRequest.builder().requestInfo(body.getRequestInfo()).measurements(measurementList).build();
        ResponseEntity<MeasurementResponse> measurementResponse = restTemplate.postForEntity(url, measurementRegistryRequest, MeasurementResponse.class);
        return measurementResponse;
    }
    public ResponseEntity<MeasurementResponse> updateMeasurements(MeasurementServiceRequest body){
        String url = mbRegistryHost+mbRegistryUpdate;
        MeasurementRequest measurementRequest = measurementServiceUtil.makeMeasurementUpdateRequest(body);
        ResponseEntity<MeasurementResponse> measurementResponse = restTemplate.postForEntity(url, measurementRequest, MeasurementResponse.class);
        return measurementResponse;
    }
    public ResponseEntity<MeasurementResponse> searchMeasurements(MeasurementSearchRequest body){
        String url = mbRegistryHost+mbRegistrySearch;
        ResponseEntity<MeasurementResponse> responseEntity = restTemplate.postForEntity(url, body, MeasurementResponse.class);
        return responseEntity;
    }

}

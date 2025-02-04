package org.egov.works.measurement.util;

import org.egov.works.measurement.config.MBServiceConfiguration;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MeasurementRegistryUtil {
    private final MBServiceConfiguration MBServiceConfiguration;
    private final MeasurementServiceUtil measurementServiceUtil;
    private final RestTemplate restTemplate;

    @Autowired
    public MeasurementRegistryUtil(MBServiceConfiguration MBServiceConfiguration, MeasurementServiceUtil measurementServiceUtil, RestTemplate restTemplate) {
        this.MBServiceConfiguration = MBServiceConfiguration;
        this.measurementServiceUtil = measurementServiceUtil;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<MeasurementResponse> createMeasurements(MeasurementServiceRequest body){
        List<Measurement> measurementList = measurementServiceUtil.convertToMeasurementList(body.getMeasurements());
        String url = MBServiceConfiguration.mbRegistryHost+ MBServiceConfiguration.mbRegistryCreate;
        MeasurementRequest measurementRegistryRequest = MeasurementRequest.builder().requestInfo(body.getRequestInfo()).measurements(measurementList).build();
        ResponseEntity<MeasurementResponse> measurementResponse = restTemplate.postForEntity(url, measurementRegistryRequest, MeasurementResponse.class);
        return measurementResponse;
    }
    public ResponseEntity<MeasurementResponse> updateMeasurements(MeasurementServiceRequest body){
        String url = MBServiceConfiguration.mbRegistryHost+ MBServiceConfiguration.mbRegistryUpdate;
        MeasurementRequest measurementRequest = measurementServiceUtil.makeMeasurementUpdateRequest(body);
        ResponseEntity<MeasurementResponse> measurementResponse = restTemplate.postForEntity(url, measurementRequest, MeasurementResponse.class);
        return measurementResponse;
    }
    public ResponseEntity<MeasurementResponse> searchMeasurements(MeasurementSearchRequest body){
        String url = MBServiceConfiguration.mbRegistryHost+ MBServiceConfiguration.mbRegistrySearch;
        ResponseEntity<MeasurementResponse> responseEntity = restTemplate.postForEntity(url, body, MeasurementResponse.class);
        return responseEntity;
    }

}

package org.egov.works.measurement.service;


import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.validator.MeasurementServiceValidator;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class MeasurementRegistry {

    @Autowired
    private MeasurementServiceValidator serviceValidator;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public List<org.egov.works.measurement.web.models.MeasurementService> changeToMeasurementService(List<Measurement> measurements) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<String> mbNumbers = getMbNumbers(measurements);
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServices = serviceRequestRepository.getMeasurementServicesFromMBSTable(namedParameterJdbcTemplate, mbNumbers);
        Map<String, org.egov.works.measurement.web.models.MeasurementService> mbNumberToServiceMap = getMbNumberToServiceMap(measurementServices);
        List<org.egov.works.measurement.web.models.MeasurementService> orderedExistingMeasurementService = serviceValidator.createOrderedMeasurementServiceList(mbNumbers, mbNumberToServiceMap);

        // Create measurement services for each measurement
        List<org.egov.works.measurement.web.models.MeasurementService> result = createMeasurementServices(measurements, orderedExistingMeasurementService);

        return result;
    }

    /**
     * enriching response for measurement-service
     * @param measurementSearchRequest
     * @return
     */
    public MeasurementServiceResponse makeSearchResponse(MeasurementSearchRequest measurementSearchRequest) {
        MeasurementServiceResponse response = new MeasurementServiceResponse();
        response.setResponseInfo(ResponseInfo.builder()
                .apiId(measurementSearchRequest.getRequestInfo().getApiId())
                .msgId(measurementSearchRequest.getRequestInfo().getMsgId())
                .ts(measurementSearchRequest.getRequestInfo().getTs())
                .status("successful")
                .build());
        return response;
    }

    public Map<String, org.egov.works.measurement.web.models.MeasurementService> getMbNumberToServiceMap(List<org.egov.works.measurement.web.models.MeasurementService> measurementServices){
        Map<String, org.egov.works.measurement.web.models.MeasurementService> mbNumberToServiceMap = new HashMap<>();
        for (org.egov.works.measurement.web.models.MeasurementService existingService : measurementServices) {
            mbNumberToServiceMap.put(existingService.getMeasurementNumber(), existingService);
        }
        return mbNumberToServiceMap;
    }
    public  List<String> getMbNumbers(List<Measurement> measurements){
        List<String> mbNumbers=new ArrayList<>();
        for(Measurement measurement:measurements){
            mbNumbers.add(measurement.getMeasurementNumber());
        }
        return mbNumbers;
    }


    private List<org.egov.works.measurement.web.models.MeasurementService> createMeasurementServices(List<Measurement> measurements, List<org.egov.works.measurement.web.models.MeasurementService> orderedExistingMeasurementService) {
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServices = new ArrayList<>();

        for (int i = 0; i < measurements.size(); i++) {
            Measurement measurement = measurements.get(i);
            org.egov.works.measurement.web.models.MeasurementService measurementService = new org.egov.works.measurement.web.models.MeasurementService();

            // Set properties of the measurement service based on the measurement object
            measurementService.setId(measurement.getId());
            measurementService.setTenantId(measurement.getTenantId());
            measurementService.setMeasurementNumber(measurement.getMeasurementNumber());
            measurementService.setPhysicalRefNumber(measurement.getPhysicalRefNumber());
            measurementService.setReferenceId(measurement.getReferenceId());
            measurementService.setEntryDate(measurement.getEntryDate());
            measurementService.setMeasures(measurement.getMeasures());
            measurementService.setDocuments(measurement.getDocuments());
            measurementService.setIsActive(measurement.getIsActive());
            measurementService.setAuditDetails(measurement.getAuditDetails());
            measurementService.setAdditionalDetails(measurement.getAdditionalDetails());

            // If an existing measurement service exists, set its workflow status
            org.egov.works.measurement.web.models.MeasurementService existingMeasurementService = orderedExistingMeasurementService.get(i);
            if (existingMeasurementService != null) {
                measurementService.setWfStatus(existingMeasurementService.getWfStatus());
            }

            measurementServices.add(measurementService);
        }

        return measurementServices;
    }
}

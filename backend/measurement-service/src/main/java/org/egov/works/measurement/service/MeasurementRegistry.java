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

    public List<org.egov.works.measurement.web.models.MeasurementSvcObject> changeToMeasurementService(List<Measurement> measurements) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<String> mbNumbers = getMbNumbers(measurements);
        List<org.egov.works.measurement.web.models.MeasurementSvcObject> measurementServices = serviceRequestRepository.getMeasurementServicesFromMBSTable(namedParameterJdbcTemplate, mbNumbers);
        Map<String, org.egov.works.measurement.web.models.MeasurementSvcObject> mbNumberToServiceMap = getMbNumberToServiceMap(measurementServices);
        List<org.egov.works.measurement.web.models.MeasurementSvcObject> orderedExistingMeasurementService = serviceValidator.createOrderedMeasurementServiceList(mbNumbers, mbNumberToServiceMap);

        // Create measurement services for each measurement
        List<org.egov.works.measurement.web.models.MeasurementSvcObject> result = createMeasurementServices(measurements, orderedExistingMeasurementService);

        return result;
    }

    //Create a MeasurementSvcObject object from a Measurement object
    private MeasurementSvcObject createMeasurementServiceFromMeasurement(Measurement measurement) {
        MeasurementSvcObject measurementService = new MeasurementSvcObject();

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

        return measurementService;
    }

    //Apply the workflow status from an existing MeasurementSvcObject object
    private void applyWorkflowStatus(org.egov.works.measurement.web.models.MeasurementSvcObject measurementService, org.egov.works.measurement.web.models.MeasurementSvcObject existingMeasurementService) {
        if (existingMeasurementService != null) {
            measurementService.setWfStatus(existingMeasurementService.getWfStatus());
        }
    }

    // Main function that uses the above two parts
    private List<org.egov.works.measurement.web.models.MeasurementSvcObject> createMeasurementServices(List<Measurement> measurements, List<org.egov.works.measurement.web.models.MeasurementSvcObject> orderedExistingMeasurementService) {
        List<org.egov.works.measurement.web.models.MeasurementSvcObject> measurementServices = new ArrayList<>();

        for (int i = 0; i < measurements.size(); i++) {
            Measurement measurement = measurements.get(i);
            org.egov.works.measurement.web.models.MeasurementSvcObject measurementService = createMeasurementServiceFromMeasurement(measurement);

            // Apply the workflow status from an existing measurement service
            org.egov.works.measurement.web.models.MeasurementSvcObject existingMeasurementService = orderedExistingMeasurementService.get(i);
            applyWorkflowStatus(measurementService, existingMeasurementService);

            measurementServices.add(measurementService);
        }

        return measurementServices;
    }


    public  List<String> getMbNumbers(List<Measurement> measurements){
        List<String> mbNumbers=new ArrayList<>();
        for(Measurement measurement:measurements){
            mbNumbers.add(measurement.getMeasurementNumber());
        }
        return mbNumbers;
    }

    public Map<String, org.egov.works.measurement.web.models.MeasurementSvcObject> getMbNumberToServiceMap(List<org.egov.works.measurement.web.models.MeasurementSvcObject> measurementServices){
        Map<String, org.egov.works.measurement.web.models.MeasurementSvcObject> mbNumberToServiceMap = new HashMap<>();
        for (org.egov.works.measurement.web.models.MeasurementSvcObject existingService : measurementServices) {
            mbNumberToServiceMap.put(existingService.getMeasurementNumber(), existingService);
        }
        return mbNumberToServiceMap;
    }

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

}

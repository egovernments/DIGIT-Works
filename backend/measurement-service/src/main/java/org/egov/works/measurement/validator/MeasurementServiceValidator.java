package org.egov.works.measurement.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.config.ErrorConfiguration;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.repository.rowmapper.MeasurementRowMapper;
import org.egov.works.measurement.service.MeasurementRegistry;
import org.egov.works.measurement.util.ContractUtil;
import org.egov.works.measurement.util.MeasurementRegistryUtil;
import org.egov.works.measurement.web.models.*;
import digit.models.coremodels.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MeasurementServiceValidator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ContractUtil contractUtil;

    @Autowired
    private Configuration configuration;

    @Autowired
    private MeasurementRowMapper rowMapper;

    @Autowired
    private ErrorConfiguration errorConfigs;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;


    @Autowired
    private MeasurementRegistry measurementRegistry;

    @Autowired
    private MeasurementRegistryUtil measurementRegistryUtil;


    @Value("${egov.filestore.host}")
    private String baseFilestoreUrl;

    @Value("${egov.filestore.endpoint}")
    private String baseFilestoreEndpoint;


    public void validateContracts(MeasurementServiceRequest measurementServiceRequest) {
        measurementServiceRequest.getMeasurements().forEach(measurement -> {
            // validate contracts
            Boolean isValidContract = contractUtil.validContract(measurement, measurementServiceRequest.getRequestInfo());

            if (!isValidContract) {
                throw errorConfigs.invalidEstimateID;
            }
        });
    }

    public void validateExistingServiceDataAndEnrich(MeasurementServiceRequest measurementServiceRequest) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<String> mbNumbers = new ArrayList<>();
        Map<String, MeasurementService> mbNumberToServiceMap = new HashMap<>();

        for (MeasurementService measurementService : measurementServiceRequest.getMeasurements()) {
            mbNumbers.add(measurementService.getMeasurementNumber());
        }

        List<MeasurementService> existingMeasurementService = serviceRequestRepository.getMeasurementServicesFromMBSTable(namedParameterJdbcTemplate, mbNumbers);
        if(existingMeasurementService.size()!=measurementServiceRequest.getMeasurements().size()){
            throw errorConfigs.measurementServiceDataNotExist;
        }

        // if wfStatus is rejected then throw error
        for(MeasurementService measurementService:existingMeasurementService){
            if(measurementService.getWfStatus().equals("REJECTED")){
                 throw errorConfigs.rejectedError(measurementService.getMeasurementNumber());
            }
        }
        // Create a map to associate mbNumbers with corresponding MeasurementRegistry objects
        for (MeasurementService existingService : existingMeasurementService) {
            mbNumberToServiceMap.put(existingService.getMeasurementNumber(), existingService);
        }

        List<MeasurementService> orderedExistingMeasurementService = createOrderedMeasurementServiceList(mbNumbers, mbNumberToServiceMap); //ordering measurementServices
        matchIdsAndMbNumber(orderedExistingMeasurementService,measurementServiceRequest.getMeasurements()); // Match ids and measurement Numbers
        setServiceAuditDetails(orderedExistingMeasurementService, measurementServiceRequest); // Set audit details for orderedExistingMeasurementService
    }

    public void matchIdsAndMbNumber(List<MeasurementService> orderedExistingMeasurementService, List<MeasurementService> measurementServices) {
        List<UUID> existingIds = orderedExistingMeasurementService.stream()
                .map(MeasurementService::getId)
                .collect(Collectors.toList());

        List<String> existingMeasurementNumbers = orderedExistingMeasurementService.stream()
                .map(MeasurementService::getMeasurementNumber)
                .collect(Collectors.toList());

        List<UUID> newIds = measurementServices.stream()
                .map(MeasurementService::getId)
                .collect(Collectors.toList());

        List<String> newMeasurementNumbers = measurementServices.stream()
                .map(MeasurementService::getMeasurementNumber)
                .collect(Collectors.toList());

        if (!existingIds.equals(newIds) || !existingMeasurementNumbers.equals(newMeasurementNumbers)) {
            throw errorConfigs.idsAndMbNumberMismatch;
        }
    }


    public List<MeasurementService> createOrderedMeasurementServiceList(List<String> mbNumbers, Map<String, MeasurementService> mbNumberToServiceMap) {
        List<MeasurementService> orderedExistingMeasurementService = new ArrayList<>();

        // Populate orderedExistingMeasurementService to match the order of mbNumbers
        for (String mbNumber : mbNumbers) {
            MeasurementService existingService = mbNumberToServiceMap.get(mbNumber);
            orderedExistingMeasurementService.add(existingService);
        }

        return orderedExistingMeasurementService;
    }

    public void setServiceAuditDetails(List<MeasurementService> measurementServiceExisting,MeasurementServiceRequest measurementServiceRequest){
        List<MeasurementService> measurementServices=measurementServiceRequest.getMeasurements();
        for(int i=0;i<measurementServiceExisting.size();i++){
            measurementServices.get(i).setAuditDetails(measurementServiceExisting.get(i).getAuditDetails());
            measurementServices.get(i).getAuditDetails().setLastModifiedBy(measurementServiceRequest.getRequestInfo().getUserInfo().getUuid());
            measurementServices.get(i).getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
            for(Measure measure:measurementServices.get(i).getMeasures()){
                measure.setAuditDetails(measurementServiceExisting.get(i).getAuditDetails());
            }
        }
        measurementServiceRequest.setMeasurements(measurementServices);
    }

    public boolean checkDocumentIdsMatch(List<String> documentIds, String responseJson) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, String> fileStoreIds = objectMapper.readValue(responseJson, Map.class);

            for (String documentId : documentIds) {
                if (!fileStoreIds.containsKey(documentId)) {
                    return false; // At least one document ID was not found
                }
            }

            return true; // All document IDs were found
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error occurred while parsing the response
        }
    }

}

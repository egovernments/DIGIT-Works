package org.egov.works.measurement.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.MBRegistryConfiguration;
import org.egov.works.measurement.kafka.MBRegistryProducer;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.util.MeasurementRegistryUtil;
import org.egov.works.measurement.util.ResponseInfoFactory;
import org.egov.works.measurement.validator.MeasurementValidator;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.egov.works.measurement.config.ErrorConfiguration.*;


@Service
@Slf4j
public class MeasurementRegistry {
    @Autowired
    private MBRegistryProducer MBRegistryProducer;
    @Autowired
    private MBRegistryConfiguration MBRegistryConfiguration;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ResponseInfoFactory responseInfoFactory;
    @Autowired
    private MeasurementValidator measurementValidator;
    @Autowired
    private MeasurementRegistryUtil measurementRegistryUtil;
    @Autowired
    private EnrichmentService enrichmentService;

    /**
     * Handles measurement create
     * @param request
     * @return
     */
    public MeasurementResponse createMeasurement(MeasurementRequest request){

        // validate tenant id
        measurementValidator.validateTenantId(request);
        // validate documents ids if present
        measurementValidator.validateDocumentIds(request.getMeasurements());
        // enrich measurements
        enrichmentService.enrichMeasurement(request);
        // push to kafka topic
        MBRegistryProducer.push(MBRegistryConfiguration.getCreateMeasurementTopic(),request);

        return  MeasurementResponse.builder().responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(),true)).measurements(request.getMeasurements()).build();

    }

    /**
     * Handles measurement update
     * @param measurementRegistrationRequest
     * @return
     */
    public MeasurementResponse updateMeasurement(MeasurementRequest measurementRegistrationRequest) {
        // Just validate tenant id
        measurementValidator.validateTenantId(measurementRegistrationRequest);

        //Validate document IDs from the measurement request
        measurementValidator.validateDocumentIds(measurementRegistrationRequest.getMeasurements());

        // Validate existing data and set audit details
        measurementValidator.validateExistingDataAndEnrich(measurementRegistrationRequest);

        // Enrich documents
        enrichmentService.enrichDocumentsForUpdate(measurementRegistrationRequest);

        //Updating Cumulative Value
        enrichmentService.handleCumulativeUpdate(measurementRegistrationRequest);

        // Create the MeasurementResponse object
        MeasurementResponse response = measurementRegistryUtil.makeUpdateResponse(measurementRegistrationRequest.getMeasurements(),measurementRegistrationRequest);

        // Push the response to the MBRegistryProducer
        MBRegistryProducer.push(MBRegistryConfiguration.getUpdateTopic(), response);

        return response;
    }


    /**
     * Handles search measurements
     */
    public List<Measurement> searchMeasurements(MeasurementCriteria searchCriteria, MeasurementSearchRequest measurementSearchRequest) {

        handleNullPagination(measurementSearchRequest);
        if (searchCriteria == null) {
            throw new CustomException(SEARCH_CRITERIA_MANDATORY_CODE, SEARCH_CRITERIA_MANDATORY_MSG);
        } else if (StringUtils.isEmpty(searchCriteria.getTenantId())) {
            throw new CustomException(TENANT_ID_MANDATORY_CODE, TENANT_ID_MANDATORY_MSG);
        }
        List<Measurement> measurements = serviceRequestRepository.getMeasurements(searchCriteria, measurementSearchRequest);
        return measurements;
    }

    public Integer getMeasurementCount(MeasurementCriteria searchCriteria) {
        return serviceRequestRepository.getCount(searchCriteria);
    }

    private void handleNullPagination(MeasurementSearchRequest body){
        if (body.getPagination() == null) {
            body.setPagination(new Pagination());
            body.getPagination().setLimit(null);
            body.getPagination().setOffSet(null);
            body.getPagination().setOrder(Pagination.OrderEnum.DESC);
            body.getPagination().setSortBy("createdtime");
        }
    }

    public MeasurementResponse createSearchResponse(MeasurementSearchRequest body){
        MeasurementResponse response = new MeasurementResponse();
        response.setResponseInfo(ResponseInfo.builder()
                .apiId(body.getRequestInfo().getApiId())
                .msgId(body.getRequestInfo().getMsgId())
                .ts(body.getRequestInfo().getTs())
                .status("successful")
                .build());
        return response;
    }

}

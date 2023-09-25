package org.egov.works.measurement.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.config.ErrorConfiguration;
import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.util.*;
//import org.egov.works.measurement.validator.MeasurementServiceValidator;
import org.egov.works.measurement.validator.MeasurementValidator;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.egov.works.measurement.web.models.Pagination;

import java.util.*;

import org.egov.works.measurement.config.Configuration;

import org.egov.works.measurement.web.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.web.models.MeasurementCriteria;


@Service
@Slf4j
public class MeasurementRegistry {
    @Autowired
    private Producer producer;
    @Autowired
    private Configuration configuration;
    @Autowired
    private ErrorConfiguration errorConfigs;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ResponseInfoFactory responseInfoFactory;
    @Autowired
    private MeasurementValidator measurementValidator;
    @Autowired
    private MeasurementRegistryUtil measurementRegistryUtil;

    /**
     * Handles measurement create
     * @param request
     * @return
     */
    public ResponseEntity<MeasurementResponse> createMeasurement(MeasurementRequest request){

        //  validate tenant id
        measurementValidator.validateTenantId(request);
        // validate documents ids if present
        measurementValidator.validateDocumentIds(request.getMeasurements());
        // enrich measurements
        measurementRegistryUtil.enrichMeasurement(request);
        // create a response
        MeasurementResponse response = MeasurementResponse.builder().responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(),true)).measurements(request.getMeasurements()).build();
        // push to kafka topic
        producer.push(configuration.getCreateMeasurementTopic(),request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
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

        //Updating Cumulative Value
        measurementRegistryUtil.handleCumulativeUpdate(measurementRegistrationRequest);

        // Create the MeasurementResponse object
        MeasurementResponse response = measurementRegistryUtil.makeUpdateResponse(measurementRegistrationRequest.getMeasurements(),measurementRegistrationRequest);

        // Push the response to the producer
        producer.push(configuration.getUpdateTopic(), response);

        return response;
    }


    /**
     * Handles search measurements
     */
    public List<Measurement> searchMeasurements(MeasurementCriteria searchCriteria, MeasurementSearchRequest measurementSearchRequest) {

        handleNullPagination(measurementSearchRequest);
        if (StringUtils.isEmpty(searchCriteria.getTenantId()) || searchCriteria == null) {
            throw errorConfigs.tenantIdRequired;
        }
        List<Measurement> measurements = serviceRequestRepository.getMeasurements(searchCriteria, measurementSearchRequest);
        return measurements;
    }


    private void handleNullPagination(MeasurementSearchRequest body){
        if (body.getPagination() == null) {
            body.setPagination(new Pagination());
            body.getPagination().setLimit(null);
            body.getPagination().setOffSet(null);
            body.getPagination().setOrder(Pagination.OrderEnum.DESC);
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

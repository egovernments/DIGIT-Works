package org.egov.works.measurement.util;

import org.apache.commons.lang.StringUtils;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.measurement.config.ErrorConfiguration.TENANT_ID_MANDATORY_CODE;
import static org.egov.works.measurement.config.ErrorConfiguration.TENANT_ID_MANDATORY_MSG;

@Component
public class MeasurementRegistryUtil {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    /**
     * Calculates the cumulative value based on the latest measurement and curr measurement details
     * @param latestMeasurement recent measurement in Measurement Book for the given contract Number
     * @param currMeasurement current measurement received as a part of create measurement request
     */
    public void calculateCumulativeValue(Measurement latestMeasurement,Measurement currMeasurement){
        Map<String, BigDecimal> targetIdtoCumulativeMap = new HashMap<>();
        for(Measure measure:latestMeasurement.getMeasures()){
            targetIdtoCumulativeMap.put(measure.getTargetId(),measure.getCumulativeValue());
        }
        for(Measure measure:currMeasurement.getMeasures()){
            if (targetIdtoCumulativeMap.containsKey(measure.getTargetId()))
                measure.setCumulativeValue( targetIdtoCumulativeMap.get(measure.getTargetId()).add(measure.getCurrentValue()));
            else
                measure.setCumulativeValue(measure.getCurrentValue());
        }
    }

    /**
     * Search for measurement ...
     * @param searchCriteria
     * @param measurementSearchRequest
     * @return
     */
    public List<Measurement> searchMeasurements(MeasurementCriteria searchCriteria, MeasurementSearchRequest measurementSearchRequest) {

        handleNullPagination(measurementSearchRequest);
        if (searchCriteria == null || StringUtils.isEmpty(searchCriteria.getTenantId())) {
            throw new CustomException(TENANT_ID_MANDATORY_CODE, TENANT_ID_MANDATORY_MSG);
        }
        List<Measurement> measurements = serviceRequestRepository.getMeasurements(searchCriteria, measurementSearchRequest);
        return measurements;
    }

    /**
     *
     * @param body
     */
    private void handleNullPagination(MeasurementSearchRequest body){
        if (body.getPagination() == null) {
            body.setPagination(new Pagination());
            body.getPagination().setLimit(null);
            body.getPagination().setOffSet(null);
            body.getPagination().setOrder(Pagination.OrderEnum.DESC);
        }
    }

    /**
     * re-calculate the cumulative value in case of update cumulative value request
     * @param latestMeasurement
     * @param currMeasurement
     */
    public void calculateCumulativeValueOnUpdate(Measurement latestMeasurement,Measurement currMeasurement){
        Map<String,BigDecimal> targetIdtoCumulativeMap = new HashMap<>();
        for(Measure measure:latestMeasurement.getMeasures()){
            targetIdtoCumulativeMap.put(measure.getTargetId(),measure.getCumulativeValue().subtract(measure.getCurrentValue()));
        }
        for(Measure measure:currMeasurement.getMeasures()){
            if (targetIdtoCumulativeMap.containsKey(measure.getTargetId()))
                measure.setCumulativeValue( targetIdtoCumulativeMap.get(measure.getTargetId()).add(measure.getCurrentValue()));
            else
                measure.setCumulativeValue(measure.getCurrentValue());
        }
    }

    /**
     *
     * @param measurements
     * @param measurementRegistrationRequest
     * @return
     */
    public MeasurementResponse makeUpdateResponse(List<Measurement> measurements,MeasurementRequest measurementRegistrationRequest) {
        MeasurementResponse response = new MeasurementResponse();
        response.setResponseInfo(ResponseInfo.builder()
                .apiId(measurementRegistrationRequest.getRequestInfo().getApiId())
                .msgId(measurementRegistrationRequest.getRequestInfo().getMsgId())
                .ts(measurementRegistrationRequest.getRequestInfo().getTs())
                .status("successful")
                .build());
        response.setMeasurements(measurements);
        return response;
    }

    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, Measurement measurement, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if(isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(measurement.getAuditDetails().getCreatedBy()).lastModifiedBy(by)
                    .createdTime(measurement.getAuditDetails().getCreatedTime()).lastModifiedTime(time).build();
    }

    public void validateDimensions(Measure measure) {
        if (measure.getLength().compareTo(BigDecimal.ZERO) == 0 && measure.getHeight().compareTo(BigDecimal.ZERO) == 0 &&
                measure.getBreadth().compareTo(BigDecimal.ZERO) == 0 && measure.getNumItems().compareTo(BigDecimal.ZERO) == 0)
            return;
        if (measure.getLength() == null || measure.getLength().compareTo(BigDecimal.ZERO) == 0) {
            measure.setLength(BigDecimal.ONE);
        }
        if (measure.getHeight() == null || measure.getHeight().compareTo(BigDecimal.ZERO) == 0) {
            measure.setHeight(BigDecimal.ONE);
        }
        if (measure.getBreadth() == null || measure.getBreadth().compareTo(BigDecimal.ZERO) == 0) {
            measure.setBreadth(BigDecimal.ONE);
        }
        if (measure.getNumItems() == null || measure.getNumItems().compareTo(BigDecimal.ZERO) == 0) {
            measure.setNumItems(BigDecimal.ONE);
        }
    }
}

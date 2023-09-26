package org.egov.works.measurement.enrichment;


import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.config.ErrorConfiguration;
import org.egov.works.measurement.util.IdgenUtil;
import org.egov.works.measurement.util.MeasurementRegistryUtil;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@Slf4j
public class MeasurementEnrichment {

    @Autowired
    private MeasurementRegistryUtil measurementRegistryUtil;
    @Autowired
    private IdgenUtil idgenUtil;
    @Autowired
    private Configuration configuration;
    @Autowired
    private ErrorConfiguration errorConfigs;
    public void enrichMeasurement(MeasurementRequest request){

        String tenantId = request.getMeasurements().get(0).getTenantId(); // each measurement should have same tenantId otherwise this will fail
        List<String> measurementNumberList = idgenUtil.getIdList(request.getRequestInfo(), tenantId, configuration.getIdName(), configuration.getIdFormat(), request.getMeasurements().size());
        List<Measurement> measurements = request.getMeasurements();

        for (int i = 0; i < measurements.size(); i++) {
            Measurement measurement = measurements.get(i);

            // enrich UUID
            measurement.setId(UUID.randomUUID());
            // enrich the Audit details
            measurement.setAuditDetails(AuditDetails.builder()
                    .createdBy(request.getRequestInfo().getUserInfo().getUuid())
                    .lastModifiedBy(request.getRequestInfo().getUserInfo().getUuid())
                    .createdTime(System.currentTimeMillis())
                    .lastModifiedTime(System.currentTimeMillis())
                    .build());

            // enrich measures in a measurement
            enrichMeasures(measurement);
            // enrich IdGen
            measurement.setMeasurementNumber(measurementNumberList.get(i));
            // enrich Cumulative value
            try {
                enrichCumulativeValue(measurement);
            }
            catch (Exception e){
                throw errorConfigs.cumulativeEnrichmentError;
            }
        }
    }

    /**
     * Helper function to enriches a measure
     * @param measurement
     */
    public void enrichMeasures(Measurement measurement){
        List<Measure> measureList = measurement.getMeasures();
        if(measurement.getDocuments()!=null){
            for(Document document:measurement.getDocuments()){
                document.setId(UUID.randomUUID().toString());
            }
        }
        for (Measure measure : measureList) {
            measure.setId(UUID.randomUUID());
            measure.setReferenceId(measurement.getId().toString());
            measure.setAuditDetails(measurement.getAuditDetails());
            measure.setCurrentValue(measure.getLength().multiply(measure.getHeight().multiply(measure.getBreadth().multiply(measure.getNumItems()))));
        }
    }

    public void enrichCumulativeValue(Measurement measurement){
        MeasurementCriteria measurementCriteria = MeasurementCriteria.builder()
                .referenceId(Collections.singletonList(measurement.getReferenceId()))
                .tenantId(measurement.getTenantId())
                .build();
        Pagination pagination= Pagination.builder().offSet(0).build();
        MeasurementSearchRequest measurementSearchRequest=MeasurementSearchRequest.builder().criteria(measurementCriteria).pagination(pagination).build();
        List<Measurement> measurementList = measurementRegistryUtil.searchMeasurements(measurementCriteria,measurementSearchRequest);
        if(!measurementList.isEmpty()){
            Measurement latestMeasurement = measurementList.get(0);
            calculateCumulativeValue(latestMeasurement,measurement);
        }
        else{
            for(Measure measure : measurement.getMeasures()){
                measure.setCumulativeValue(measure.getCurrentValue());
            }
        }
    }
    public void calculateCumulativeValue(Measurement latestMeasurement,Measurement currMeasurement){
        Map<String, BigDecimal> targetIdtoCumulativeMap = new HashMap<>();
        for(Measure measure:latestMeasurement.getMeasures()){
            targetIdtoCumulativeMap.put(measure.getTargetId(),measure.getCumulativeValue());
        }
        for(Measure measure:currMeasurement.getMeasures()){
            measure.setCumulativeValue( targetIdtoCumulativeMap.get(measure.getTargetId()).add(measure.getCurrentValue()));
        }
    }

    public void enrichCumulativeValueOnUpdate(Measurement measurement){
        MeasurementCriteria measurementCriteria = MeasurementCriteria.builder()
                .referenceId(Collections.singletonList(measurement.getReferenceId()))
                .tenantId(measurement.getTenantId())
                .build();
        Pagination pagination= Pagination.builder().offSet(0).build();
        MeasurementSearchRequest measurementSearchRequest=MeasurementSearchRequest.builder().criteria(measurementCriteria).pagination(pagination).build();
        List<Measurement> measurementList =measurementRegistryUtil.searchMeasurements(measurementCriteria,measurementSearchRequest);
        if(!measurementList.isEmpty()){
            Measurement latestMeasurement = measurementList.get(0);
            calculateCumulativeValueOnUpdate(latestMeasurement,measurement);
        }
        else{
            for(Measure measure : measurement.getMeasures()){
                measure.setCumulativeValue(measure.getCurrentValue());
            }
        }
    }
    public void calculateCumulativeValueOnUpdate(Measurement latestMeasurement,Measurement currMeasurement){
        Map<String,BigDecimal> targetIdtoCumulativeMap = new HashMap<>();
        for(Measure measure:latestMeasurement.getMeasures()){
            targetIdtoCumulativeMap.put(measure.getTargetId(),measure.getCumulativeValue().subtract(measure.getCurrentValue()));
        }
        for(Measure measure:currMeasurement.getMeasures()){
            measure.setCumulativeValue( targetIdtoCumulativeMap.get(measure.getTargetId()).add(measure.getCurrentValue()));
        }
    }
}

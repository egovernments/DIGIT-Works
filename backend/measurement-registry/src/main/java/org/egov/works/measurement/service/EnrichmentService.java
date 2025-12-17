package org.egov.works.measurement.service;

import org.egov.common.contract.models.Document;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.MBRegistryConfiguration;
import org.egov.works.measurement.util.IdgenUtil;
import org.egov.works.measurement.util.MeasurementRegistryUtil;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.egov.works.measurement.config.ErrorConfiguration.CUMULATIVE_ENRICHMENT_ERROR_CODE;
import static org.egov.works.measurement.config.ErrorConfiguration.CUMULATIVE_ENRICHMENT_ERROR_MSG;

@Component
public class EnrichmentService {

    @Autowired
    private MBRegistryConfiguration MBRegistryConfiguration;
    @Autowired
    private IdgenUtil idgenUtil;
    @Autowired
    private MeasurementRegistryUtil measurementRegistryUtil;

    /**
     * Enriches a measurement with UUID , audit details, Idgen , Cumulative value
     * @param request
     */
    public void enrichMeasurement(MeasurementRequest request){

        String tenantId = request.getMeasurements().get(0).getTenantId();
        List<String> measurementNumberList = idgenUtil.getIdList(request.getRequestInfo(), tenantId, MBRegistryConfiguration.getIdName(), MBRegistryConfiguration.getIdFormat(), request.getMeasurements().size());
        List<Measurement> measurements = request.getMeasurements();

        for (int i = 0; i < measurements.size(); i++) {
            Measurement measurement = measurements.get(i);

            // enrich UUID
            measurement.setId(UUID.randomUUID().toString());
            // enrich the Audit details
            measurement.setAuditDetails(measurementRegistryUtil.getAuditDetails(request.getRequestInfo().getUserInfo().getUuid(),measurement,true));

            // enrich measures in a measurement
            enrichMeasures(measurement);
            // enrich IdGen
            measurement.setMeasurementNumber(measurementNumberList.get(i));
            // enrich Cumulative value
            try {
                enrichCumulativeValue(measurement);
            }
            catch (Exception e){
                throw new CustomException(CUMULATIVE_ENRICHMENT_ERROR_CODE, CUMULATIVE_ENRICHMENT_ERROR_MSG);
            }
        }
    }

    /**
     * Enriches a measure with UUID, ReferenceId of measurement, audit details, currentValue
     * @param measurement contain as part of measurement request
     */
    public void enrichMeasures(Measurement measurement){
        List<Measure> measureList = measurement.getMeasures();
        if(measurement.getDocuments()!=null){
            for(Document document:measurement.getDocuments()){
                document.setId(UUID.randomUUID().toString());
            }
        }
        for (Measure measure : measureList) {
            measure.setId(UUID.randomUUID().toString());
            measure.setReferenceId(measurement.getId());
            measure.setAuditDetails(measurement.getAuditDetails());
            measurementRegistryUtil.validateDimensions(measure);
            measure.setCurrentValue(measure.getLength().multiply(measure.getHeight().multiply(measure.getBreadth().multiply(measure.getNumItems()))));
        }
    }

    /**
     * Enriches cumulative value based on the cumulative value of the latest measurement book and current Value
     * @param measurement received as measurement request
     */
    public void enrichCumulativeValue(Measurement measurement){
        MeasurementCriteria measurementCriteria = MeasurementCriteria.builder()
                .referenceId(Collections.singletonList(measurement.getReferenceId()))
                .isActive(true)
                .tenantId(measurement.getTenantId())
                .build();
        Pagination pagination= Pagination.builder().offSet(0).build();
        MeasurementSearchRequest measurementSearchRequest=MeasurementSearchRequest.builder().criteria(measurementCriteria).pagination(pagination).build();
        List<Measurement> measurementList = measurementRegistryUtil.searchMeasurements(measurementCriteria,measurementSearchRequest);
        if(!measurementList.isEmpty()){
            Measurement latestMeasurement = measurementList.get(0);
            measurementRegistryUtil.calculateCumulativeValue(latestMeasurement,measurement);
        }
        else{
            for(Measure measure : measurement.getMeasures()){
                measure.setCumulativeValue(measure.getCurrentValue());
            }
        }
    }

    /**
     *
     * @param measurement
     */
    public void enrichCumulativeValueOnUpdate(Measurement measurement){
        MeasurementCriteria measurementCriteria = MeasurementCriteria.builder()
                .referenceId(Collections.singletonList(measurement.getReferenceId()))
                .isActive(true)
                .tenantId(measurement.getTenantId())
                .build();
        Pagination pagination= Pagination.builder().offSet(0).build();
        MeasurementSearchRequest measurementSearchRequest=MeasurementSearchRequest.builder().criteria(measurementCriteria).pagination(pagination).build();
        List<Measurement> measurementList = measurementRegistryUtil.searchMeasurements(measurementCriteria,measurementSearchRequest);
        if(!measurementList.isEmpty()){
            Measurement latestMeasurement = measurementList.get(0);
            measurementRegistryUtil.calculateCumulativeValueOnUpdate(latestMeasurement,measurement);
        }
        else{
            for(Measure measure : measurement.getMeasures()){
                measure.setCumulativeValue(measure.getCurrentValue());
            }
        }
    }

    /**
     * Updates the cumulative value
     * @param measurementRequest
     */
    public  void handleCumulativeUpdate(MeasurementRequest measurementRequest){
        for(Measurement measurement:measurementRequest.getMeasurements()){
            try {
                enrichCumulativeValueOnUpdate(measurement);
            }
            catch (Exception  e){
                throw new CustomException(CUMULATIVE_ENRICHMENT_ERROR_CODE, CUMULATIVE_ENRICHMENT_ERROR_MSG);
            }
        }
    }

    public void enrichDocumentsForUpdate(MeasurementRequest measurementRequest){
        for(Measurement measurement:measurementRequest.getMeasurements()){
            if(measurement.getDocuments()!=null){
                for(Document document:measurement.getDocuments()){
                    if (document.getId() == null)
                        document.setId(UUID.randomUUID().toString());
                }
            }
        }
    }
}

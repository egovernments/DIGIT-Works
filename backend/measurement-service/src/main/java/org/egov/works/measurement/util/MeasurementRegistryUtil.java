package org.egov.works.measurement.util;

import org.apache.commons.lang.StringUtils;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.config.ErrorConfiguration;
import org.egov.works.measurement.enrichment.MeasurementEnrichment;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private MeasurementEnrichment measurementEnrichment;


    public List<Measurement> searchMeasurements(MeasurementCriteria searchCriteria, MeasurementSearchRequest measurementSearchRequest) {

        handleNullPagination(measurementSearchRequest);
        if (StringUtils.isEmpty(searchCriteria.getTenantId()) || searchCriteria == null) {
            throw errorConfigs.tenantIdRequired;
        }
        List<Measurement> measurements = serviceRequestRepository.getMeasurements(searchCriteria, measurementSearchRequest);
        return measurements;
    }

    public void handleNullPagination(MeasurementSearchRequest body){
        if (body.getPagination() == null) {
            body.setPagination(new Pagination());
            body.getPagination().setLimit(null);
            body.getPagination().setOffSet(null);
            body.getPagination().setOrder(Pagination.OrderEnum.DESC);
        }
    }

    public  void handleCumulativeUpdate(MeasurementRequest measurementRequest){
        for(Measurement measurement:measurementRequest.getMeasurements()){
            try {
                measurementEnrichment.enrichCumulativeValueOnUpdate(measurement);
            }
            catch (Exception  e){
                throw errorConfigs.cumulativeEnrichmentError;
            }
        }
    }

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

}

package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.services.common.models.contract.Contract;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateResponse;
import org.egov.works.services.common.models.measurement.Measurement;
import org.egov.works.util.ContractUtil;
import org.egov.works.util.EstimateUtil;
import org.egov.works.util.MeasurementUtil;
import org.egov.works.validator.UtilizationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FetcherService {

    private final MeasurementUtil measurementUtil;
    private final EstimateUtil estimateUtil;
    private final ContractUtil contractUtil;
    private final UtilizationValidator utilizationValidator;

    @Autowired
    public FetcherService(MeasurementUtil measurementUtil, EstimateUtil estimateUtil, ContractUtil contractUtil, UtilizationValidator utilizationValidator) {
        this.measurementUtil = measurementUtil;
        this.estimateUtil = estimateUtil;
        this.contractUtil = contractUtil;
        this.utilizationValidator = utilizationValidator;
    }

    public Measurement fetchAndValidateMeasurements(String id, String tenantId, RequestInfo requestInfo) {
        List<Measurement> measurements = measurementUtil.fetchMeasurements(id, tenantId, requestInfo);
        utilizationValidator.validateMeasurements(measurements);
        return measurements.get(0);
    }

    Contract fetchAndValidateContracts(String contractNumber, String tenantId, RequestInfo requestInfo) {
        List<Contract> contracts = contractUtil.fetchContracts(contractNumber, tenantId, requestInfo);
        utilizationValidator.validateContracts(contracts);

        return contracts.get(0);
    }

    Estimate fetchAndValidateEstimates(String id, String tenantId, RequestInfo requestInfo, String statementType) {
        EstimateResponse estimateResponse = estimateUtil.getEstimate(id, tenantId,statementType, requestInfo);
        utilizationValidator.validateEstimates(estimateResponse.getEstimates());
        return estimateResponse.getEstimates().get(0);
    }


}

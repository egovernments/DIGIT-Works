package org.egov.works.validator;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.contract.Contract;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.measurement.Measurement;
import org.egov.works.web.models.StatementCreateRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class UtilizationValidator {

    public void validateUtilizationStatement(StatementCreateRequest request) {





    }

    public void validateMeasurements(List<Measurement> measurements) {
        if (CollectionUtils.isEmpty(measurements)) {
            throw new CustomException("MEASUREMENTS_NOT_FOUND", "Measurements not found");
        }
    }

    public void validateContracts(List<Contract> contracts) {
        if (CollectionUtils.isEmpty(contracts)) {
            throw new CustomException("CONTRACTS_NOT_FOUND", "Contracts not found");
        }
    }

    public void validateEstimates(List<Estimate> estimates) {
        if (CollectionUtils.isEmpty(estimates)) {
            throw new CustomException("ESTIMATES_NOT_FOUND", "Estimates not found");
        }
    }
}

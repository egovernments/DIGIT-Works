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

import static org.egov.works.config.ErrorConfiguration.*;

@Component
@Slf4j
public class UtilizationValidator {

    public void validateUtilizationStatement(StatementCreateRequest request) {





    }

    public void validateMeasurements(List<Measurement> measurements) {
        if (CollectionUtils.isEmpty(measurements)) {
            throw new CustomException(MEASUREMENTS_NOT_FOUND_KEY, MEASUREMENTS_NOT_FOUND_MSG);
        }
    }

    public void validateContracts(List<Contract> contracts) {
        if (CollectionUtils.isEmpty(contracts)) {
            throw new CustomException(CONTRACTS_NOT_FOUND_KEY, CONTRACTS_NOT_FOUND_MSG);
        }
    }

    public void validateEstimates(List<Estimate> estimates) {
        if (CollectionUtils.isEmpty(estimates)) {
            throw new CustomException(ESTIMATES_NOT_FOUND_KEY, ESTIMATES_NOT_FOUND_MSG);
        }
    }
}

package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.digit.expense.web.models.BillCalculationCriteria;
import org.egov.digit.expense.web.models.BillCalculationRequest;
import org.egov.digit.expense.web.models.BillCalculationResponse;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculatorUtil {

    private final ObjectMapper mapper;
    private final ServiceRequestRepository restRepo;
    private final Configuration configs;

    @Autowired
    public CalculatorUtil(ObjectMapper mapper, ServiceRequestRepository restRepo, Configuration configs) {
        this.mapper = mapper;
        this.restRepo = restRepo;
        this.configs = configs;
    }

    /**
     * Calls the health-expense-calculator service to fetch calculated bills
     * @param calculationRequest BillCalculationRequest object
     * @return BillCalculationResponse with calculated bills
     */
    public BillCalculationResponse getBills(BillCalculationRequest calculationRequest) {


        StringBuilder uri = new StringBuilder()
                .append(configs.getCalculatorHost())
                .append(configs.getCalculatePath());

        Object response = restRepo.fetchResult(uri, calculationRequest);

        try {
            return mapper.convertValue(response, BillCalculationResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse calculator service response: " + e.getMessage());
        }
    }
}

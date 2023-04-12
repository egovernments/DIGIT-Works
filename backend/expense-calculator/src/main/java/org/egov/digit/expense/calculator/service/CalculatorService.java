package org.egov.digit.expense.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.calculator.enrichment.CalculatorEnrichment;
import org.egov.digit.expense.calculator.util.ResponseInfoFactory;
import org.egov.digit.expense.calculator.validator.CalculatorServiceValidator;
import org.egov.digit.expense.calculator.web.models.Calculation;
import org.egov.digit.expense.calculator.web.models.CalculationRequest;
import org.egov.digit.expense.calculator.web.models.CalculationResponse;
import org.egov.digit.expense.calculator.web.models.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CalculatorService {

    @Autowired
    private CalculatorServiceValidator calculatorServiceValidator;

    @Autowired
    private CalculatorEnrichment calculatorEnrichment;

    @Autowired
    private WageSeekerBillGeneratorService wageSeekerBillGeneratorService;

    public Calculation createEstimate(CalculationRequest calculationRequest) {
        calculatorServiceValidator.validateCreateCalculatorEstimateRequest(calculationRequest);
        //calculatorEnrichment.enrichCalculatorEstimateCreateRequest(calculationRequest);
        Calculation calculation = calculateEstimates(calculationRequest);

        return calculation;
    }

    private Calculation calculateEstimates(CalculationRequest calculationRequest){
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        Criteria criteria = calculationRequest.getCriteria();
        return wageSeekerBillGeneratorService.calculateEstimates(requestInfo,criteria);
    }
}

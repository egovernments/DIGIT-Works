package org.egov.digit.expense.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.enrichment.ExpenseCalculatorEnrichment;
import org.egov.digit.expense.calculator.validator.ExpenseCalculatorServiceValidator;
import org.egov.digit.expense.calculator.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExpenseCalculatorService {

    @Autowired
    private ExpenseCalculatorServiceValidator expenseCalculatorServiceValidator;
    @Autowired
    private ExpenseCalculatorEnrichment expenseCalculatorEnrichment;
    @Autowired
    private WageSeekerBillGeneratorService wageSeekerBillGeneratorService;

    public Calculation calculateEstimates(CalculationRequest calculationRequest) {
        expenseCalculatorServiceValidator.validateCreateCalculatorEstimateRequest(calculationRequest);
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        Criteria criteria = calculationRequest.getCriteria();
        return wageSeekerBillGeneratorService.calculateEstimates(requestInfo,criteria);
    }

    public void createAndPostWageSeekerBill(MusterRollRequest musterRollRequest){
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();

        wageSeekerBillGeneratorService.createAndPostWageSeekerBill(requestInfo, musterRoll);
    }

}

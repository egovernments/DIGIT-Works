package org.egov.digit.expense.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.enrichment.ExpenseCalculatorEnrichment;
import org.egov.digit.expense.calculator.kafka.ExpenseCalculatorProducer;
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
    @Autowired
    private SupervisionBillGeneratorService supervisionBillGeneratorService;
    @Autowired
    private ExpenseCalculatorProducer expenseCalculatorProducer;
    @Autowired
    private ExpenseCalculatorConfiguration config;

    public Calculation calculateEstimates(CalculationRequest calculationRequest) {
        expenseCalculatorServiceValidator.validateCalculatorEstimateRequest(calculationRequest);
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        Criteria criteria = calculationRequest.getCriteria();

        if(criteria.getMusterRollId() != null && !criteria.getMusterRollId().isEmpty()) {
            return wageSeekerBillGeneratorService.calculateEstimates(requestInfo, criteria);
        }
        else {
            return supervisionBillGeneratorService.calculateEstimate(requestInfo, criteria);
        }
    }

    public Calculation calculate(CalculationRequest calculationRequest) {
        Calculation calculation = calculateEstimates(calculationRequest);
        expenseCalculatorProducer.push(config.getCalculatorCreateTopic(),calculation);
        return calculation;
    }

    public void createAndPostWageSeekerBill(MusterRollRequest musterRollRequest){


        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();

        wageSeekerBillGeneratorService.createAndPostWageSeekerBill(requestInfo, musterRoll);
    }

}

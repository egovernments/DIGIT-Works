package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillCalculatorRequestInfoWrapper;
import org.egov.digit.expense.calculator.web.models.BillResponse;
import org.egov.digit.expense.calculator.web.models.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BillUtils {

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    @Autowired
    private ObjectMapper mapper;

    public BillResponse postBill(RequestInfo requestInfo, Bill bill, Workflow workflow) {
        StringBuilder url = getBillCreateURI();

        BillCalculatorRequestInfoWrapper requestInfoWrapper = BillCalculatorRequestInfoWrapper.builder()
                                                                .requestInfo(requestInfo)
                                                                .bill(bill)
                                                                .workflow(workflow)
                                                                .build();

        Object responseObj = restRepo.fetchResult(url, requestInfoWrapper);
        return mapper.convertValue(responseObj, BillResponse.class);
    }

    private StringBuilder getBillCreateURI() {
        StringBuilder builder = new StringBuilder(configs.getBillHost());
        builder.append(configs.getBillCreateEndPoint());
        return builder;
    }
}

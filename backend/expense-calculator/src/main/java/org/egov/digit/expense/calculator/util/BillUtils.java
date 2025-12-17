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

@Component
@Slf4j
public class BillUtils {

    private final ServiceRequestRepository restRepo;

    private final ExpenseCalculatorConfiguration configs;

    private final ObjectMapper mapper;

    @Autowired
    public BillUtils(ServiceRequestRepository restRepo, ExpenseCalculatorConfiguration configs, ObjectMapper mapper) {
        this.restRepo = restRepo;
        this.configs = configs;
        this.mapper = mapper;
    }

    public BillResponse postCreateBill(RequestInfo requestInfo, Bill bill, Workflow workflow) {
        StringBuilder url = getBillCreateURI();
        return postBill(requestInfo,bill,workflow,url);
    }

    public BillResponse postUpdateBill(RequestInfo requestInfo, Bill bill, Workflow workflow) {
        StringBuilder url = getBillUpdateURI();
        return postBill(requestInfo,bill,workflow,url);
    }

    private BillResponse postBill(RequestInfo requestInfo, Bill bill, Workflow workflow, StringBuilder url) {
        // Update workflow object because in expense service it's using core service workflow
        Workflow expenseWorkflow1 = Workflow.builder()
                .action(workflow.getAction())
                .assignees(workflow.getAssignees())
                .documents(workflow.getDocuments())
                .comments(workflow.getComments())
                .build();
        BillCalculatorRequestInfoWrapper requestInfoWrapper = BillCalculatorRequestInfoWrapper.builder()
                .requestInfo(requestInfo)
                .bill(bill)
                .workflow(expenseWorkflow1)
                .build();
        log.info("Posting Bill to expense service:" + requestInfoWrapper.toString());
        Object responseObj = restRepo.fetchResult(url, requestInfoWrapper);
        if(responseObj!=null)
        	log.info("Received Bill Response: " + responseObj.toString());
        return mapper.convertValue(responseObj, BillResponse.class);
    }
    
   
    private StringBuilder getBillCreateURI() {
        StringBuilder builder = new StringBuilder(configs.getBillHost());
        builder.append(configs.getBillCreateEndPoint());
        return builder;
    }

    private StringBuilder getBillUpdateURI() {
        StringBuilder builder = new StringBuilder(configs.getBillHost());
        builder.append(configs.getBillUpdateEndPoint());
        return builder;
    }
}

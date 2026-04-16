package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.common.contract.workflow.*;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillDetailRequest;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class WorkflowUtil {

    private final ServiceRequestRepository repository;

    private final ObjectMapper mapper;

    private final Configuration configs;

    @Autowired
    public WorkflowUtil(ServiceRequestRepository repository, ObjectMapper mapper, Configuration configs) {
        this.repository = repository;
        this.mapper = mapper;
        this.configs = configs;
    }


    /**
    * Searches the BussinessService corresponding to the businessServiceCode
    * Returns applicable BussinessService for the given parameters
    * @param requestInfo
    * @param tenantId
    * @param businessServiceCode
    * @return
    */
    public BusinessService getBusinessService(RequestInfo requestInfo, String tenantId, String businessServiceCode) {

        StringBuilder url = getSearchURLWithParams(tenantId, businessServiceCode);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object result = repository.fetchResult(url, requestInfoWrapper);
        BusinessServiceResponse response;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException("BUSINESSSERVICE_NOT_FOUND", "The businessService " + businessServiceCode + " is not found");

        return response.getBusinessServices().get(0);
    }

    /**
    * Creates url for search based on given tenantId and businessServices
    * @param tenantId
    * @param businessService
    * @return
    */
    private StringBuilder getSearchURLWithParams(String tenantId, String businessService) {
        StringBuilder url = new StringBuilder(configs.getWfHost());
        url.append(configs.getWfBusinessServiceSearchPath());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&businessServices=");
        url.append(businessService);
        return url;
    }

    /**
    * Method to take the ProcessInstanceRequest as parameter and set resultant status
    * @param workflowRequest
    * @param billRequest
    * @return
    */
    public State callWorkFlow(ProcessInstanceRequest workflowRequest, BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        ProcessInstance pi = workflowRequest.getProcessInstances().get(0);

        log.info("WF_TRANSITION | BEFORE | entity=BILL | billId={} | businessId={} | businessService={} | tenantId={} | action={} | currentStatus={}",
                bill.getId(), pi.getBusinessId(), pi.getBusinessService(), pi.getTenantId(), pi.getAction(), bill.getStatus());

        try {
            ProcessInstanceResponse response;
            StringBuilder url = new StringBuilder(configs.getWfHost().concat(configs.getWfTransitionPath()));
            Object optional = repository.fetchResult(url, workflowRequest);
            response = mapper.convertValue(optional, ProcessInstanceResponse.class);
            billRequest.getBill().setProcessInstance(response.getProcessInstances().get(0));
            State newState = response.getProcessInstances().get(0).getState();

            log.info("WF_TRANSITION | AFTER | entity=BILL | billId={} | businessId={} | action={} | newStatus={}",
                    bill.getId(), pi.getBusinessId(), pi.getAction(), newState.getApplicationStatus());

            return newState;
        } catch (Exception e) {
            log.error("WF_TRANSITION | FAILED | entity=BILL | billId={} | businessId={} | businessService={} | tenantId={} | action={} | currentStatus={} | error={}",
                    bill.getId(), pi.getBusinessId(), pi.getBusinessService(), pi.getTenantId(), pi.getAction(), bill.getStatus(), e.getMessage());
            throw e;
        }
    }

    public State callWorkFlow(ProcessInstanceRequest workflowRequest, BillDetailRequest billDetailRequest) {
        BillDetail detail = billDetailRequest.getBillDetail();
        ProcessInstance pi = workflowRequest.getProcessInstances().get(0);

        log.info("WF_TRANSITION | BEFORE | entity=BILL_DETAIL | detailId={} | businessId={} | businessService={} | tenantId={} | action={} | currentStatus={}",
                detail.getId(), pi.getBusinessId(), pi.getBusinessService(), pi.getTenantId(), pi.getAction(), detail.getStatus());

        try {
            ProcessInstanceResponse response;
            StringBuilder url = new StringBuilder(configs.getWfHost().concat(configs.getWfTransitionPath()));
            Object optional = repository.fetchResult(url, workflowRequest);
            response = mapper.convertValue(optional, ProcessInstanceResponse.class);
            billDetailRequest.getBillDetail().setProcessInstance(response.getProcessInstances().get(0));
            State newState = response.getProcessInstances().get(0).getState();

            log.info("WF_TRANSITION | AFTER | entity=BILL_DETAIL | detailId={} | businessId={} | action={} | newStatus={}",
                    detail.getId(), pi.getBusinessId(), pi.getAction(), newState.getApplicationStatus());

            return newState;
        } catch (Exception e) {
            log.error("WF_TRANSITION | FAILED | entity=BILL_DETAIL | detailId={} | businessId={} | businessService={} | tenantId={} | action={} | currentStatus={} | error={}",
                    detail.getId(), pi.getBusinessId(), pi.getBusinessService(), pi.getTenantId(), pi.getAction(), detail.getStatus(), e.getMessage());
            throw e;
        }
    }
    
	public ProcessInstanceResponse searchWorkflowForBusinessIds(List<String> businessIds, String tenantId, RequestInfo requestInfo) {
    	
    	StringBuilder url = new StringBuilder().append(configs.getWfHost());
    	url.append(configs.getWfProcessInstanceSearchPath());
    	url.append("?tenantId=");
    	url.append(tenantId);
    	url.append("&businessIds=");
		url.append(businessIds.toString().replace("[", "").replace("]", ""));
    	
    	RequestInfoWrapper infoWrapper = new RequestInfoWrapper();
    	infoWrapper.setRequestInfo(requestInfo);
    	Object optional = repository.fetchResult(url, infoWrapper);
    	return mapper.convertValue(optional, ProcessInstanceResponse.class);
    }
    
    public ProcessInstanceRequest prepareWorkflowRequestForBill(BillRequest billRequest) {
    	
    	Bill bill = billRequest.getBill();
    	Workflow workflowFromRequest = billRequest.getWorkflow();
    	List<User> assignes = new ArrayList<>();

        // Changed check from assignes to workflowFromRequest assignee object because it's checking incorrect object
		if (!CollectionUtils.isEmpty(workflowFromRequest.getAssignes()) && !workflowFromRequest.getAssignes().isEmpty())
			for (String userId : workflowFromRequest.getAssignes()) {

				User user = User.builder()
						.tenantId(bill.getTenantId())
						.uuid(userId)
						.build();

				assignes.add(user);
			}
    	
    	ProcessInstance processInstance = ProcessInstance.builder()
    			.documents(workflowFromRequest.getDocuments())
    			.moduleName(configs.getExpenseWorkflowModuleName())
    			.businessService(bill.getBusinessService())
    			.comment(workflowFromRequest.getComments())
    			.action(workflowFromRequest.getAction())
    			.businessId(bill.getBillNumber())
    			.tenantId(bill.getTenantId())
    			.assignes(assignes) 
    			.build();
    	
    	return ProcessInstanceRequest.builder()
    			.processInstances(Collections.singletonList(processInstance))
    			.requestInfo(billRequest.getRequestInfo())
    			.build();
    }

    public ProcessInstanceRequest prepareWorkflowRequestForBillDetail(BillDetailRequest billDetailRequest) {

        BillDetail billDetail = billDetailRequest.getBillDetail();
        Workflow workflowFromRequest = billDetailRequest.getWorkflow();
        List<User> assignes = new ArrayList<>();

        if (!CollectionUtils.isEmpty(workflowFromRequest.getAssignes()) && !workflowFromRequest.getAssignes().isEmpty())
            for (String userId : workflowFromRequest.getAssignes()) {

                User user = User.builder()
                        .tenantId(billDetail.getTenantId())
                        .uuid(userId)
                        .build();

                assignes.add(user);
            }

        ProcessInstance processInstance = ProcessInstance.builder()
                .documents(workflowFromRequest.getDocuments())
                .moduleName(configs.getExpenseWorkflowModuleName())
                .businessService(billDetailRequest.getBusinessService())
                .comment(workflowFromRequest.getComments())
                .action(workflowFromRequest.getAction())
                .businessId(billDetail.getId())
                .tenantId(billDetail.getTenantId())
                .assignes(assignes)
                .build();

        return ProcessInstanceRequest.builder()
                .processInstances(Collections.singletonList(processInstance))
                .requestInfo(billDetailRequest.getRequestInfo())
                .build();
    }
}
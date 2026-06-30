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
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.egov.digit.expense.config.Constants.*;

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
     * Returns true if the WF error is the async-persistence race (INVALID ACTION / No valid action).
     * These indicate the transition was already applied — caller should search WF state to reconcile.
     */
    public boolean isRetryableWfError(Exception e) {
        Throwable t = e;
        while (t != null) {
            String msg = Optional.ofNullable(t.getMessage()).orElse("");
            if (msg.contains(WF_INVALID_ACTION_MSG_1)
                    || msg.contains(WF_INVALID_ACTION_MSG_2)
                    || msg.contains(WF_INVALID_ACTION_MSG_3)) {
                return true;
            }
            t = t.getCause();
        }
        return false;
    }

    /**
     * Makes a single WF transition attempt with no retry or sleep.
     * Callers that need to handle INVALID_ACTION must catch it and call
     * {@link #searchCurrentWfState} to reconcile — or return RETRY to the scheduler.
     * No Thread.sleep here: blocking the Kafka consumer or scheduler thread degrades all processing.
     */
    private State callWorkFlowWithRetry(ProcessInstanceRequest workflowRequest,
                                        java.util.function.Supplier<State> workflowCall) {
        try {
            return workflowCall.get();
        } catch (Exception e) {
            if (isRetryableWfError(e)) {
                log.warn("WF_TRANSITION | INVALID_ACTION — transition may already be done; caller must search WF state | error={}",
                        e.getMessage());
            } else {
                e.printStackTrace();
                log.error("WF_TRANSITION | FAILED (non-retryable) | error={}", e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Searches the current workflow state for a given business entity.
     * Used to reconcile in-memory state after an INVALID_ACTION failure.
     * Returns null if the search itself fails or finds no process instance.
     */
    public State searchCurrentWfState(String businessId, String tenantId, RequestInfo requestInfo) {
        try {
            ProcessInstanceResponse resp = searchWorkflowForBusinessIds(
                    Collections.singletonList(businessId), tenantId, requestInfo);
            if (resp == null || resp.getProcessInstances() == null || resp.getProcessInstances().isEmpty()) {
                log.warn("WF_STATE_SEARCH | no process instance found for businessId={} tenantId={}", businessId, tenantId);
                return null;
            }
            State state = resp.getProcessInstances().get(0).getState();
            log.info("WF_STATE_SEARCH | businessId={} currentState={}", businessId, state.getApplicationStatus());
            return state;
        } catch (Exception e) {
            log.error("WF_STATE_SEARCH | failed for businessId={} tenantId={}: {}", businessId, tenantId, e.getMessage());
            return null;
        }
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

        // Fetch actual WF state. If WF already differs from in-memory the transition was applied
        // by another thread/task — sync the status and return without re-calling the action.
        State currentWfState = searchCurrentWfState(pi.getBusinessId(), pi.getTenantId(), billRequest.getRequestInfo());
        if (currentWfState != null) {
            Status actualStatus = Status.fromValue(currentWfState.getApplicationStatus());
            if (actualStatus != bill.getStatus()) {
                log.info("WF_STATE_RECONCILE | entity=BILL | billId={} | inMemory={} | actual={} | action={} — returning reconciled state without transition",
                        bill.getId(), bill.getStatus(), actualStatus, pi.getAction());
                bill.setStatus(actualStatus);
                return currentWfState;
            }
        }

        log.info("WF_TRANSITION | BEFORE | entity=BILL | billId={} | businessId={} | businessService={} | tenantId={} | action={} | currentStatus={}",
                bill.getId(), pi.getBusinessId(), pi.getBusinessService(), pi.getTenantId(), pi.getAction(), bill.getStatus());

        return callWorkFlowWithRetry(workflowRequest, () -> {
            StringBuilder url = new StringBuilder(configs.getWfHost().concat(configs.getWfTransitionPath()));
            Object optional = repository.fetchResult(url, workflowRequest);
            ProcessInstanceResponse response = mapper.convertValue(optional, ProcessInstanceResponse.class);
            billRequest.getBill().setProcessInstance(response.getProcessInstances().get(0));
            State newState = response.getProcessInstances().get(0).getState();
            log.info("WF_TRANSITION | AFTER | entity=BILL | billId={} | businessId={} | action={} | newStatus={}",
                    bill.getId(), pi.getBusinessId(), pi.getAction(), newState.getApplicationStatus());
            return newState;
        });
    }

    public State callWorkFlow(ProcessInstanceRequest workflowRequest, BillDetailRequest billDetailRequest) {
        BillDetail detail = billDetailRequest.getBillDetail();
        ProcessInstance pi = workflowRequest.getProcessInstances().get(0);

        // Fetch actual WF state. If WF already differs from in-memory the transition was applied
        // by another thread/task — sync the status and return without re-calling the action.
        State currentWfState = searchCurrentWfState(detail.getId(), detail.getTenantId(), billDetailRequest.getRequestInfo());
        if (currentWfState != null) {
            Status actualStatus = Status.fromValue(currentWfState.getApplicationStatus());
            if (actualStatus != detail.getStatus()) {
                log.info("WF_STATE_RECONCILE | entity=BILL_DETAIL | detailId={} | inMemory={} | actual={} | action={} — returning reconciled state without transition",
                        detail.getId(), detail.getStatus(), actualStatus, pi.getAction());
                detail.setStatus(actualStatus);
                return currentWfState;
            }
        }

        log.info("WF_TRANSITION | BEFORE | entity=BILL_DETAIL | detailId={} | businessId={} | businessService={} | tenantId={} | action={} | currentStatus={}",
                detail.getId(), pi.getBusinessId(), pi.getBusinessService(), pi.getTenantId(), pi.getAction(), detail.getStatus());

        return callWorkFlowWithRetry(workflowRequest, () -> {
            StringBuilder url = new StringBuilder(configs.getWfHost().concat(configs.getWfTransitionPath()));
            Object optional = repository.fetchResult(url, workflowRequest);
            ProcessInstanceResponse response = mapper.convertValue(optional, ProcessInstanceResponse.class);
            billDetailRequest.getBillDetail().setProcessInstance(response.getProcessInstances().get(0));
            State newState = response.getProcessInstances().get(0).getState();
            log.info("WF_TRANSITION | AFTER | entity=BILL_DETAIL | detailId={} | businessId={} | action={} | newStatus={}",
                    detail.getId(), pi.getBusinessId(), pi.getAction(), newState.getApplicationStatus());
            return newState;
        });
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

    public Map<String, State> callWorkFlowBatch(RequestInfo requestInfo, String tenantId,
                                                 String businessService, String action,
                                                 List<String> businessIds) {
        List<ProcessInstance> processInstances = businessIds.stream()
                .map(id -> ProcessInstance.builder()
                        .moduleName(configs.getExpenseWorkflowModuleName())
                        .businessService(businessService)
                        .action(action)
                        .businessId(id)
                        .tenantId(tenantId)
                        .assignes(Collections.emptyList())
                        .build())
                .collect(Collectors.toList());
        ProcessInstanceRequest batchRequest = ProcessInstanceRequest.builder()
                .requestInfo(requestInfo)
                .processInstances(processInstances)
                .build();
        StringBuilder url = new StringBuilder(configs.getWfHost().concat(configs.getWfTransitionPath()));
        Object result = repository.fetchResult(url, batchRequest);
        ProcessInstanceResponse response = mapper.convertValue(result, ProcessInstanceResponse.class);
        return response.getProcessInstances().stream()
                .filter(pi -> pi.getState() != null && pi.getBusinessId() != null)
                .collect(Collectors.toMap(
                        ProcessInstance::getBusinessId,
                        ProcessInstance::getState,
                        (a, b) -> b));
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
package org.egov.digit.expense.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.common.contract.workflow.*;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkflowService {

    private final Configuration serviceConfiguration;

    private final ServiceRequestRepository repository;

    private final ObjectMapper mapper;

    @Autowired
    public WorkflowService(Configuration serviceConfiguration, ServiceRequestRepository repository, ObjectMapper mapper) {
        this.serviceConfiguration = serviceConfiguration;
        this.repository = repository;
        this.mapper = mapper;
    }

    /* Call the workflow service with the given action and update the status
     * return the updated status of the application
     *
     */
    public String updateWorkflowStatus(BillRequest contractRequest) {
        Bill contract = contractRequest.getBill();
        log.info("Update work flow status. ContractId ["+contract.getId()+"]");
        ProcessInstance processInstance = getProcessInstanceForContract(contractRequest);
        ProcessInstanceRequest workflowRequest = new ProcessInstanceRequest(contractRequest.getRequestInfo(), Collections.singletonList(processInstance));
        ProcessInstance processInstanceResponse = callWorkFlow(workflowRequest,contract.getId());

        //contract workflow Status
        contract.setWfStatus(processInstanceResponse.getState().getApplicationStatus());
        
        // Fetch currentProcessInstance from workflow process search for inbox config
        log.info("Work flow status updated. ContractId ["+contract.getId()+"]");
        return processInstanceResponse.getState().getApplicationStatus();
    }

    private ProcessInstance getProcessInstanceForContract(BillRequest request) {
        Bill contract = request.getBill();
        log.info("Get process instance for contract. ContractId ["+contract.getId()+"]");
        Workflow workflow = request.getWorkflow();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setBusinessId(contract.getBillNumber());
        processInstance.setAction(request.getWorkflow().getAction());
        //TODO: Below needs to change from configuration
        processInstance.setModuleName("expense");
        processInstance.setTenantId(contract.getTenantId());
        processInstance.setBusinessService(getBusinessService(request).getBusinessService());
        processInstance.setComment(workflow.getComments());

        if (!CollectionUtils.isEmpty(workflow.getAssignes())) {
            List<User> users = new ArrayList<>();

            workflow.getAssignes().forEach(uuid -> {
                User user = new User();
                user.setUuid(uuid);
                users.add(user);
            });

            processInstance.setAssignes(users);
        }

        log.info("Process instance created for contract. ContractId ["+contract.getId()+"]");
        return processInstance;
    }

    /**
     * Method to integrate with workflow
     * <p>
     * take the ProcessInstanceRequest as paramerter to call wf-service
     * <p>
     * and return wf-response to sets the resultant status
     */
    private ProcessInstance callWorkFlow(ProcessInstanceRequest workflowReq, String id) {
        log.info("Call workflow service for contractId ["+id+"]");
        ProcessInstanceResponse response = null;
        StringBuilder url = new StringBuilder(serviceConfiguration.getWfHost().concat(serviceConfiguration.getWfTransitionPath()));
        Object optional = repository.fetchResult(url, workflowReq);
        response = mapper.convertValue(optional, ProcessInstanceResponse.class);
        return response.getProcessInstances().get(0);
    }

    /*
     * Should return the applicable BusinessService for the given request
     *
     */
    public BusinessService getBusinessService(BillRequest contractRequest) {
        String tenantId = contractRequest.getBill().getTenantId();
        //TODO: MOdify below line to fetch businessService name from MDMS or configure in application.properties
        StringBuilder url = getSearchURLWithParams(tenantId, "works.wages");
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Object result = repository.fetchResult(url, requestInfo);
        BusinessServiceResponse response = null;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException("BUSINESSSERVICE_DOES_NOT_EXIST", "The businessService : " + contractRequest.getBill().getBusinessService() + " doesn't exist");

        return response.getBusinessServices().get(0);
    }

    /**
     * Creates url for search based on given tenantId and businessservices
     *
     * @param tenantId        The tenantId for which url is generated
     * @param businessService The businessService for which url is generated
     * @return The search url
     */

    private StringBuilder getSearchURLWithParams(String tenantId, String businessService) {

        StringBuilder url = new StringBuilder(serviceConfiguration.getWfHost());
        url.append(serviceConfiguration.getWfBusinessServiceSearchPath());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&businessServices=");
        url.append(businessService);
        return url;
    }

    /*
     * Should return the applicable processInstance for the given request
     *
     */
    public ProcessInstance getProcessInstance(BillRequest contractRequest) {
        String tenantId = contractRequest.getBill().getTenantId();
        String businessId = contractRequest.getBill().getBillNumber();
        StringBuilder url = getProcessSearchURLWithParams(tenantId, businessId);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(contractRequest.getRequestInfo()).build();
        Object result = repository.fetchResult(url, requestInfoWrapper);
        ProcessInstanceResponse response = null;
        try {
            response = mapper.convertValue(result, ProcessInstanceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getProcessInstances()))
            throw new CustomException("PROCESSINSTANCE_DOES_NOT_EXIST", "The businessId : " + businessId + " doesn't exist");

        return response.getProcessInstances().get(0);
    }

    /**
     * Creates url for search based on given tenantId and businessId
     *
     * @param tenantId   The tenantId for which url is generated
     * @param businessId The businessId for which url is generated
     * @return The search url
     */

    private StringBuilder getProcessSearchURLWithParams(String tenantId, String businessId) {

        StringBuilder url = new StringBuilder(serviceConfiguration.getWfHost());
        url.append(serviceConfiguration.getWfProcessInstanceSearchPath());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&businessIds=");
        url.append(businessId);
        url.append("&history=false");
        return url;
    }

}

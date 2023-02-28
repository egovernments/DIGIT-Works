package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.*;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.Contract;
import org.egov.works.web.models.ContractRequest;
import org.egov.works.web.models.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WorkflowService {

    @Autowired
    private ContractServiceConfiguration serviceConfiguration;

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private ObjectMapper mapper;

    /* Call the workflow service with the given action and update the status
     * return the updated status of the application
     *
     */
    public String updateWorkflowStatus(ContractRequest contractRequest) {
        ProcessInstance processInstance = getProcessInstanceForContract(contractRequest);
        ProcessInstanceRequest workflowRequest = new ProcessInstanceRequest(contractRequest.getRequestInfo(), Collections.singletonList(processInstance));
        ProcessInstance processInstanceResponse = callWorkFlow(workflowRequest);
        //contract workflow Status
        contractRequest.getContract().setWfStatus(processInstanceResponse.getState().getApplicationStatus());
        // Fetch currentProcessInstance from workflow process search for inbox config
        contractRequest.getContract().setProcessInstance(processInstanceResponse);
        return processInstanceResponse.getState().getApplicationStatus();
    }

    private ProcessInstance getProcessInstanceForContract(ContractRequest request) {

        Contract contract = request.getContract();
        Workflow workflow = request.getWorkflow();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setBusinessId(contract.getContractNumber());
        processInstance.setAction(request.getWorkflow().getAction());
        processInstance.setModuleName(serviceConfiguration.getContractWFModuleName());
        processInstance.setTenantId(contract.getTenantId());
        processInstance.setBusinessService(getBusinessService(request).getBusinessService());
        /* processInstance.setDocuments(request.getWorkflow().getVerificationDocuments());*/
        processInstance.setComment(workflow.getComment());

        if (!CollectionUtils.isEmpty(workflow.getAssignees())) {
            List<User> users = new ArrayList<>();

            workflow.getAssignees().forEach(uuid -> {
                User user = new User();
                user.setUuid(uuid);
                users.add(user);
            });

            processInstance.setAssignes(users);
        }

        return processInstance;
    }

    /**
     * Method to integrate with workflow
     * <p>
     * take the ProcessInstanceRequest as paramerter to call wf-service
     * <p>
     * and return wf-response to sets the resultant status
     */
    private ProcessInstance callWorkFlow(ProcessInstanceRequest workflowReq) {

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
    public BusinessService getBusinessService(ContractRequest contractRequest) {
        String tenantId = contractRequest.getContract().getTenantId();
        StringBuilder url = getSearchURLWithParams(tenantId, serviceConfiguration.getContractWFBusinessService());
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Object result = repository.fetchResult(url, requestInfo);
        BusinessServiceResponse response = null;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException("BUSINESSSERVICE_DOESN'T_EXIST", "The businessService : " + serviceConfiguration.getContractWFBusinessService() + " doesn't exist");

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
    public ProcessInstance getProcessInstance(ContractRequest contractRequest) {
        String tenantId = contractRequest.getContract().getTenantId();
        String businessId = contractRequest.getContract().getContractNumber();
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
            throw new CustomException("PROCESSINSTANCE_DOESN'T_EXIST", "The businessId : " + businessId + " doesn't exist");

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

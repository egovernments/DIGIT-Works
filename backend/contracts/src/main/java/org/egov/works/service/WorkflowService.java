package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.common.contract.workflow.*;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.Contract;
import org.egov.works.web.models.ContractRequest;
import org.egov.works.web.models.Status;
import org.egov.works.web.models.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.egov.works.util.ContractServiceConstants.*;

@Service
@Slf4j
public class WorkflowService {

    private final ContractServiceConfiguration serviceConfiguration;

    private final ServiceRequestRepository repository;

    private final ObjectMapper mapper;

    @Autowired
    public WorkflowService(ContractServiceConfiguration serviceConfiguration, ServiceRequestRepository repository, ObjectMapper mapper) {
        this.serviceConfiguration = serviceConfiguration;
        this.repository = repository;
        this.mapper = mapper;
    }

    /* Call the workflow service with the given action and update the status
     * return the updated status of the application
     *
     */
    public String updateWorkflowStatus(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        log.info("Update work flow status. ContractId ["+contract.getId()+"]");
        ProcessInstance processInstance = getProcessInstanceForContract(contractRequest);
        ProcessInstanceRequest workflowRequest = new ProcessInstanceRequest(contractRequest.getRequestInfo(), Collections.singletonList(processInstance));
        ProcessInstance processInstanceResponse = callWorkFlow(workflowRequest,contract.getId());
        //contract workflow Status. Set the Wf status to be the actual state of the workflow
        contract.setWfStatus(processInstanceResponse.getState().getState());
        //Set the application status to be ACTIVE or INACTIVE from the application status in workflow config
        contract.setStatus(Status.fromValue(processInstanceResponse.getState().getApplicationStatus()));
        // Fetch currentProcessInstance from workflow process search for inbox config
        contract.setProcessInstance(processInstanceResponse);
        log.info("Work flow status updated. ContractId ["+contract.getId()+"]");
        return processInstanceResponse.getState().getApplicationStatus();
    }

    private ProcessInstance getProcessInstanceForContract(ContractRequest request) {
        Contract contract = request.getContract();
        log.info("Get process instance for contract. ContractId ["+contract.getId()+"]");
        Workflow workflow = request.getWorkflow();

        ProcessInstance processInstance = new ProcessInstance();
        if (request.getContract().getBusinessService() != null && (request.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)
                ||request.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_ESTIMATE))) {
            processInstance.setBusinessId(contract.getSupplementNumber());
        }
        else {
            processInstance.setBusinessId(contract.getContractNumber());
        }

        processInstance.setAction(request.getWorkflow().getAction());
        processInstance.setModuleName(serviceConfiguration.getContractWFModuleName());
        processInstance.setTenantId(contract.getTenantId());
        processInstance.setBusinessService(getBusinessService(request).getBusinessService());
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

        if (!CollectionUtils.isEmpty(workflow.getDocuments())) {
            processInstance.setDocuments(workflow.getDocuments());
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
    public BusinessService getBusinessService(ContractRequest contractRequest) {
        String tenantId = contractRequest.getContract().getTenantId();
        StringBuilder url= new StringBuilder();

        if (contractRequest.getContract().getBusinessService() != null
                && contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)){
            url = getSearchURLWithParams(tenantId, serviceConfiguration.getContractTimeExtensionWFBusinessService());
        }

        if(contractRequest.getContract().getBusinessService() != null
                && contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_ESTIMATE)) {
            url = getSearchURLWithParams(tenantId, serviceConfiguration.getContractRevisionContractWFBusinessService());
        }
            if(contractRequest.getContract().getBusinessService() != null
                    && contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_BUSINESS_SERVICE)) {
                url = getSearchURLWithParams(tenantId, serviceConfiguration.getContractWFBusinessService());
            }
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Object result = repository.fetchResult(url, requestInfo);
        BusinessServiceResponse response = null;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException("BUSINESSSERVICE_DOES_NOT_EXIST", "The businessService : " + serviceConfiguration.getContractWFBusinessService() + " doesn't exist");

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
        String businessId = null;
        if (contractRequest.getContract().getBusinessService()!=null && (contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)
                || contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_ESTIMATE))) {
            businessId = contractRequest.getContract().getSupplementNumber();
        }else {
            businessId = contractRequest.getContract().getContractNumber();
        }
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


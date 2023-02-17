package org.egov.works.util;

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
public class WorkflowUtil {

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ContractServiceConfiguration contractServiceConfiguration;


    /**
     * Searches the BussinessService corresponding to the businessServiceCode
     * Returns applicable BussinessService for the given parameters
     *
     */
    public BusinessService getBusinessService(ContractRequest contractRequest) {

        String tenantId = contractRequest.getContract().getTenantId();
        StringBuilder url = getSearchURLWithParams(tenantId, contractServiceConfiguration.getContractWFBusinessService());

        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Object result = repository.fetchResult(url, requestInfo);
        BusinessServiceResponse response = null;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException("BUSINESSSERVICE_NOT_FOUND", "The businessService " +
                    contractServiceConfiguration.getContractWFBusinessService() + " is not found");

        return response.getBusinessServices().get(0);
    }


    public String updateWorkflowStatus(ContractRequest contractRequest) {
        ProcessInstance processInstance = getProcessInstanceForContract(contractRequest);
        ProcessInstanceRequest workflowRequest = new ProcessInstanceRequest(contractRequest.getRequestInfo(), Collections.singletonList(processInstance));
        State state = callWorkFlow(workflowRequest);

        //set workflow status
        contractRequest.getContract().setWfStatus(state.getApplicationStatus());
        return state.getApplicationStatus();
    }

    private StringBuilder getSearchURLWithParams(String tenantId, String businessService) {
        StringBuilder url = new StringBuilder(contractServiceConfiguration.getWfHost());
        url.append(contractServiceConfiguration.getWfBusinessServiceSearchPath());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&businessServices=");
        url.append(businessService);
        return url;
    }


    private ProcessInstance getProcessInstanceForContract(ContractRequest contractRequest) {

        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Workflow workflow = contractRequest.getWorkflow();
        Contract contract = contractRequest.getContract();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setBusinessId(contract.getContractNumber());
        processInstance.setAction(workflow.getAction());
        processInstance.setModuleName(contractServiceConfiguration.getContractWFModuleName());
        processInstance.setTenantId(contract.getTenantId());
        processInstance.setBusinessService(getBusinessService(contractRequest).getBusinessService());
//        processInstance.setDocuments(workflow.getVerificationDocuments());
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
     * Method to take the ProcessInstanceRequest as parameter and set resultant status
     *
     */
    private State callWorkFlow(ProcessInstanceRequest workflowReq) {
        ProcessInstanceResponse response = null;
        StringBuilder url = new StringBuilder(contractServiceConfiguration.getWfHost().concat(contractServiceConfiguration.getWfTransitionPath()));
        Object optional = repository.fetchResult(url, workflowReq);
        response = mapper.convertValue(optional, ProcessInstanceResponse.class);
        return response.getProcessInstances().get(0).getState();
    }
}
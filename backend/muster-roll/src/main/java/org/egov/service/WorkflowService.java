package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.*;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.MusterRollServiceConstants;
import org.egov.web.models.MusterRoll;
import org.egov.web.models.MusterRollRequest;
import org.egov.web.models.Workflow;
import org.egov.web.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class WorkflowService {

    @Autowired
    private MusterRollServiceConfiguration serviceConfiguration;

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private ObjectMapper mapper;

    /* Call the workflow service with the given action and update the status
     * return the updated status of the application
     *
     */
    public String updateWorkflowStatus(MusterRollRequest musterRollRequest) {
        ProcessInstance processInstance = getProcessInstanceForMusterRoll(musterRollRequest);
        ProcessInstanceRequest workflowRequest = new ProcessInstanceRequest(musterRollRequest.getRequestInfo(), Collections.singletonList(processInstance));
        ProcessInstance processInstanceResponse = callWorkFlow(workflowRequest);
        musterRollRequest.getMusterRoll().setMusterRollStatus(processInstanceResponse.getState().getState());
        //musterRollStatus
        musterRollRequest.getMusterRoll().setStatus(Status.fromValue(processInstanceResponse.getState().getApplicationStatus()));
        // Fetch currentProcessInstance from workflow process search for inbox config
        musterRollRequest.getMusterRoll().setProcessInstance(processInstanceResponse);
        return processInstanceResponse.getState().getApplicationStatus();
    }

    private ProcessInstance getProcessInstanceForMusterRoll(MusterRollRequest request) {

        MusterRoll musterRoll = request.getMusterRoll();
        Workflow workflow = request.getWorkflow();
        if(workflow.getAction().equals(MusterRollServiceConstants.ACTION_SENDBACK) && CollectionUtils.isEmpty(workflow.getAssignees())) {
            String assignee = null;
            Boolean statusFound = false;
            List<ProcessInstance> processInstanceList = callWorkFlowForAssignees(request);
            String nextState = getNextStateValueForProcessInstance(processInstanceList.get(0));
            for(ProcessInstance processInstance: processInstanceList){
                if((processInstance.getState().getUuid() != null) && (processInstance.getState().getUuid().equals(nextState)) && (statusFound != true)) {
                    statusFound = true;
                    if(processInstance.getAssignes() != null){
                        List<String> uuids = new ArrayList<>();
                        assignee = processInstance.getAssignes().get(0).getUuid();
                        uuids.add(assignee);
                        workflow.setAssignees(uuids);
                    }
                }
            }
        }
        if(workflow.getAction().equals(MusterRollServiceConstants.ACTION_SENDBACKTOORIGINATOR) && CollectionUtils.isEmpty(workflow.getAssignees())){
            List<String> uuids = new ArrayList<>();
            uuids.add(musterRoll.getAuditDetails().getCreatedBy());
            workflow.setAssignees(uuids);
        }

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setBusinessId(musterRoll.getMusterRollNumber());
        processInstance.setAction(request.getWorkflow().getAction());
        processInstance.setModuleName(serviceConfiguration.getMusterRollWFModuleName());
        processInstance.setTenantId(musterRoll.getTenantId());
        //processInstance.setBusinessService(getBusinessService(request).getBusinessService());
        processInstance.setBusinessService(serviceConfiguration.getMusterRollWFBusinessService());
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

        if (!CollectionUtils.isEmpty(workflow.getDocuments())) {
            processInstance.setDocuments(workflow.getDocuments());
        }

        return processInstance;
    }
    private String getNextStateValueForProcessInstance(ProcessInstance processInstance){
        List<Action> actions = processInstance.getState().getActions();
        String nextState = null;
        for(Action action: actions) {
            if (action.getAction().equals("SENDBACK")) {
                nextState = action.getNextState();
            }
        }
        return nextState;
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
    private List<ProcessInstance> callWorkFlowForAssignees(MusterRollRequest musterRollRequest) {
        log.info("WorkflowService::callWorkFlow");
        ProcessInstanceResponse response = null;
        StringBuilder url = getprocessInstanceHistorySearchURL(musterRollRequest.getMusterRoll().getTenantId(), musterRollRequest.getMusterRoll().getMusterRollNumber(), true);
        Object optional = repository.fetchResult(url, musterRollRequest);
        response = mapper.convertValue(optional, ProcessInstanceResponse.class);
        return response.getProcessInstances();
    }

    /*
     * Should return the applicable BusinessService for the given request
     *
     */
    public BusinessService getBusinessService(MusterRollRequest musterRollRequest) {
        String tenantId = musterRollRequest.getMusterRoll().getTenantId();
        StringBuilder url = getSearchURLWithParams(tenantId, serviceConfiguration.getMusterRollWFBusinessService());
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        Object result = repository.fetchResult(url, requestInfo);
        BusinessServiceResponse response = null;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException("BUSINESSSERVICE_DOESN'T_EXIST", "The businessService : " + serviceConfiguration.getMusterRollWFBusinessService() + " doesn't exist");

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
    public StringBuilder getprocessInstanceHistorySearchURL(String tenantId, String musterRollNumber, boolean history) {
        log.info("WorkflowService::getprocessInstanceSearchURL");
        StringBuilder url = new StringBuilder(serviceConfiguration.getWfHost());
        url.append(serviceConfiguration.getWfProcessInstanceSearchPath());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&businessIds=");
        url.append(musterRollNumber);
        url.append("&history=");
        url.append(history);
        return url;
    }

    /*
     * Should return the applicable processInstance for the given request
     *
     */
    public ProcessInstance getProcessInstance(MusterRollRequest musterRollRequest) {
        String tenantId = musterRollRequest.getMusterRoll().getTenantId();
        String businessId = musterRollRequest.getMusterRoll().getMusterRollNumber();
        StringBuilder url = getProcessSearchURLWithParams(tenantId, businessId);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(musterRollRequest.getRequestInfo()).build();
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
     * @param tenantId     The tenantId for which url is generated
     * @param businessId   The businessId for which url is generated
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

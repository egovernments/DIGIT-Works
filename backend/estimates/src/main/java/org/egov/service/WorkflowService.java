package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.common.contract.workflow.*;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.EstimateServiceConstant;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkflowService {

    private final EstimateServiceConfiguration serviceConfiguration;

    private final ServiceRequestRepository repository;

    private final ObjectMapper mapper;
    private static final String PARSING_ERROR = "PARSING ERROR";
    private static final String TENANT_ID = "?tenantId=";

    @Autowired
    public WorkflowService(EstimateServiceConfiguration serviceConfiguration, ServiceRequestRepository repository, ObjectMapper mapper) {
        this.serviceConfiguration = serviceConfiguration;
        this.repository = repository;
        this.mapper = mapper;
    }


    /*
     * Should return the applicable BusinessService for the given request
     *
     */
    public BusinessService getBusinessService(EstimateRequest estimateRequest) {
        log.info("WorkflowService::getBusinessService");
        String tenantId = estimateRequest.getEstimate().getTenantId();
        StringBuilder url = getSearchURLWithParams(tenantId, serviceConfiguration.getEstimateWFBusinessService());
        RequestInfo requestInfo = estimateRequest.getRequestInfo();
        Object result = repository.fetchResult(url, requestInfo);
        BusinessServiceResponse response = null;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException(PARSING_ERROR, "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException("BUSINESSSERVICE_DOESN'T_EXIST", "The businessService : " + serviceConfiguration.getEstimateWFBusinessService() + " doesn't exist");

        return response.getBusinessServices().get(0);
    }


    /* Call the workflow service with the given action and update the status
     * return the updated status of the application
     *
     */
    public String updateWorkflowStatus(EstimateRequest estimateRequest) {
        log.info("WorkflowService::updateWorkflowStatus");
        ProcessInstance processInstance = getProcessInstanceForEstimate(estimateRequest);
        ProcessInstanceRequest workflowRequest = new ProcessInstanceRequest(estimateRequest.getRequestInfo(), Collections.singletonList(processInstance));
        State state = callWorkFlow(workflowRequest);
        estimateRequest.getEstimate().setWfStatus(state.getState());
        estimateRequest.getEstimate().setStatus(Estimate.StatusEnum.fromValue(state.getApplicationStatus()));
        estimateRequest.getEstimate().setProcessInstances(processInstance);
        return state.getApplicationStatus();
    }



    public void validateAssignee(EstimateRequest estimateRequest) {

        /* Call HRMS service and validate of the assignee belongs to same department
         * as the employee assigning it
         *
         */

    }

    /**
     * Creates url for search based on given tenantId and businessservices
     *
     * @param tenantId        The tenantId for which url is generated
     * @param businessService The businessService for which url is generated
     * @return The search url
     */

    private StringBuilder getSearchURLWithParams(String tenantId, String businessService) {
        log.info("WorkflowService::getSearchURLWithParams");
        StringBuilder url = new StringBuilder(serviceConfiguration.getWfHost());
        url.append(serviceConfiguration.getWfBusinessServiceSearchPath());
        url.append(TENANT_ID);
        url.append(tenantId);
        url.append("&businessServices=");
        url.append(businessService);
        return url;
    }

    public List<EstimateRequest> enrichWorkflow(RequestInfo requestInfo, List<EstimateRequest> estimateRequestList) {
        log.info("WorkflowService::enrichWorkflow");
        Map<String, List<EstimateRequest>> tenantIdToServiceWrapperMap = getTenantIdToServiceWrapperMap(estimateRequestList);

        List<EstimateRequest> enrichedServiceWrappers = new ArrayList<>();

        for (Map.Entry<String, List<EstimateRequest>> entry : tenantIdToServiceWrapperMap.entrySet()) {
            String tenantId = entry.getKey();

            List<String> estimateNumbers = new ArrayList<>();

            List<EstimateRequest> tenantSpecificWrappers = tenantIdToServiceWrapperMap.get(tenantId);

            tenantSpecificWrappers.forEach(estimateWrapper ->
                estimateNumbers.add(estimateWrapper.getEstimate().getEstimateNumber())
            );

            RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

            StringBuilder searchUrl = getprocessInstanceSearchURL(tenantId, StringUtils.join(estimateNumbers, ','));
            Object result = repository.fetchResult(searchUrl, requestInfoWrapper);


            ProcessInstanceResponse processInstanceResponse = null;
            try {
                processInstanceResponse = mapper.convertValue(result, ProcessInstanceResponse.class);
            } catch (IllegalArgumentException e) {
                throw new CustomException(PARSING_ERROR, "Failed to parse response of workflow processInstance search");
            }

            if (CollectionUtils.isEmpty(processInstanceResponse.getProcessInstances()) || processInstanceResponse.getProcessInstances().size() != estimateNumbers.size())
                throw new CustomException("WORKFLOW_NOT_FOUND", "The workflow object is not found");

            Map<String, Workflow> businessIdToWorkflow = getWorkflow(processInstanceResponse.getProcessInstances());

            tenantSpecificWrappers.forEach(estimateWrapper ->
                estimateWrapper.setWorkflow(businessIdToWorkflow.get(estimateWrapper.getEstimate().getEstimateNumber()))
            );

            enrichedServiceWrappers.addAll(tenantSpecificWrappers);
        }

        return enrichedServiceWrappers;

    }

    private Map<String, List<EstimateRequest>> getTenantIdToServiceWrapperMap(List<EstimateRequest> estimateRequestList) {
        log.info("WorkflowService::getTenantIdToServiceWrapperMap");
        Map<String, List<EstimateRequest>> resultMap = new HashMap<>();
        for (EstimateRequest estimateRequest : estimateRequestList) {
            if (resultMap.containsKey(estimateRequest.getEstimate().getTenantId())) {
                resultMap.get(estimateRequest.getEstimate().getTenantId()).add(estimateRequest);
            } else {
                List<EstimateRequest> estimateWrapperList = new ArrayList<>();
                estimateWrapperList.add(estimateRequest);
                resultMap.put(estimateRequest.getEstimate().getTenantId(), estimateWrapperList);
            }
        }
        return resultMap;
    }

    /*
     * Enriches ProcessInstance Object for workflow
     *
     * @param request
     */

    private ProcessInstance getProcessInstanceForEstimate(EstimateRequest request) {
        log.info("WorkflowService::getProcessInstanceForEstimate");
        Estimate estimate = request.getEstimate();
        Workflow workflow = request.getWorkflow();

        ProcessInstance processInstance = new ProcessInstance();
        if(estimate.getBusinessService() != null && estimate.getBusinessService().equals(serviceConfiguration.getRevisionEstimateBusinessService())){
            processInstance.setBusinessId(estimate.getRevisionNumber());
        }else{
            processInstance.setBusinessId(estimate.getEstimateNumber());
        }
        processInstance.setAction(request.getWorkflow().getAction());
        processInstance.setModuleName(serviceConfiguration.getEstimateWFModuleName());
        processInstance.setTenantId(estimate.getTenantId());
        processInstance.setBusinessService(serviceConfiguration.getEstimateWFBusinessService());
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
        if (!CollectionUtils.isEmpty(workflow.getDocuments())) {
            processInstance.setDocuments(workflow.getDocuments());
        }
        return processInstance;
    }

    /*
     * @param processInstances
     */

    public Map<String, Workflow> getWorkflow(List<ProcessInstance> processInstances) {
        log.info("WorkflowService::getWorkflow");
        Map<String, Workflow> businessIdToWorkflow = new HashMap<>();

        processInstances.forEach(processInstance -> {
            List<String> userIds = null;

            if (!CollectionUtils.isEmpty(processInstance.getAssignes())) {
                userIds = processInstance.getAssignes().stream().map(User::getUuid).collect(Collectors.toList());
            }

            Workflow workflow = Workflow.builder()
                    .action(processInstance.getAction())
                    .assignes(userIds)
                    .comments(processInstance.getComment())
                    /*.verificationDocuments(processInstance.getDocuments())*/
                    .build();

            businessIdToWorkflow.put(processInstance.getBusinessId(), workflow);
        });

        return businessIdToWorkflow;
    }

    /**
     * Method to integrate with workflow
     * <p>
     * take the ProcessInstanceRequest as paramerter to call wf-service
     * <p>
     * and return wf-response to sets the resultant status
     */
    private State callWorkFlow(ProcessInstanceRequest workflowReq) {
        log.info("WorkflowService::callWorkFlow");
        ProcessInstanceResponse response = null;
        StringBuilder url = new StringBuilder(serviceConfiguration.getWfHost().concat(serviceConfiguration.getWfTransitionPath()));
        Object optional = repository.fetchResult(url, workflowReq);
        response = mapper.convertValue(optional, ProcessInstanceResponse.class);
        return response.getProcessInstances().get(0).getState();
    }


    public StringBuilder getprocessInstanceSearchURL(String tenantId, String estimateNumber) {
        log.info("WorkflowService::getprocessInstanceSearchURL");
        StringBuilder url = new StringBuilder(serviceConfiguration.getWfHost());
        url.append(serviceConfiguration.getWfProcessInstanceSearchPath());
        url.append(TENANT_ID);
        url.append(tenantId);
        url.append("&businessIds=");
        url.append(estimateNumber);
        return url;
    }

    public ProcessInstanceResponse getProcessInstanceOfWorkFlowWithoutHistory(EstimateRequest estimateRequest) {
        log.info("WorkflowService::getProcessInstanceOfWorkFlowWithoutHistory");
        Estimate estimate = estimateRequest.getEstimate();
        RequestInfo requestInfo = estimateRequest.getRequestInfo();

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        StringBuilder searchUrl;
        if (estimate.getBusinessService() != null && estimate.getBusinessService().equals(serviceConfiguration.getRevisionEstimateBusinessService())) {
            searchUrl = getProcessInstanceSearchURLWithHistory(
                    estimate.getTenantId()
                    , estimate.getRevisionNumber()
                    , Boolean.FALSE);
        }
        else {
            searchUrl = getProcessInstanceSearchURLWithHistory(
                    estimate.getTenantId()
                    , estimate.getEstimateNumber()
                    , Boolean.FALSE);
        }

        Object result = repository.fetchResult(searchUrl, requestInfoWrapper);

        ProcessInstanceResponse processInstanceResponse = null;
        try {
            processInstanceResponse = mapper.convertValue(result, ProcessInstanceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException(PARSING_ERROR, "Failed to parse response of workflow processInstance search");
        }

        if (CollectionUtils.isEmpty(processInstanceResponse.getProcessInstances()))
            throw new CustomException("WORKFLOW_NOT_FOUND", "The workflow object is not found");

        return processInstanceResponse;
    }

    public StringBuilder getProcessInstanceSearchURLWithHistory(String tenantId, String estimateNumber, boolean history) {
        log.info("WorkflowService::getprocessInstanceSearchURLWithHistory");
        StringBuilder url = new StringBuilder();
        url.append(serviceConfiguration.getWfHost())
                .append(serviceConfiguration.getWfProcessInstanceSearchPath())
                .append(TENANT_ID).append(tenantId)
                .append("&businessIds=").append(estimateNumber)
                .append("&history=").append(history);
        return url;
    }

}

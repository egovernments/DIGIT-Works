package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.*;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.LOIConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.LetterOfIndent;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.egov.works.web.models.LetterOfIndentRequestWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkflowService {

    @Autowired
    private LOIConfiguration serviceConfiguration;

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private ObjectMapper mapper;


    /*
     * Should return the applicable BusinessService for the given request
     *
     */
    public BusinessService getBusinessService(LetterOfIndentRequest loiRequest) {
        String tenantId = loiRequest.getLetterOfIndent().getTenantId();
        StringBuilder url = getSearchURLWithParams(tenantId, serviceConfiguration.getWorkflowLOIBusinessServiceName());
        RequestInfo requestInfo = loiRequest.getRequestInfo();
        Object result = repository.fetchResult(url, requestInfo);
        BusinessServiceResponse response = null;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException("BUSINESSSERVICE_DOESN'T_EXIST", "The businessService : " + serviceConfiguration.getWorkflowLOIBusinessServiceName() + " doesn't exist");

        return response.getBusinessServices().get(0);
    }


    /* Call the workflow service with the given action and update the status
     * return the updated status of the application
     *
     */
    public String updateWorkflowStatus(LetterOfIndentRequest request) {
        ProcessInstance processInstance = getProcessInstanceForLOI(request);
        ProcessInstanceRequest workflowRequest = new ProcessInstanceRequest(request.getRequestInfo(), Collections.singletonList(processInstance));
        State state = callWorkFlow(workflowRequest);
        request.getLetterOfIndent().setLetterStatus(state.getApplicationStatus());
        return state.getApplicationStatus();
    }


    public void validateAssignee(LetterOfIndentRequest loiRequest) {

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

        StringBuilder url = new StringBuilder(serviceConfiguration.getWorkflowHost());
        url.append(serviceConfiguration.getWfBusinessServiceSearchPath());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&businessServices=");
        url.append(businessService);
        return url;
    }

    public List<LetterOfIndentRequest> enrichWorkflow(RequestInfo requestInfo, List<LetterOfIndentRequest> loiRequestList) {

        //
        Map<String, List<LetterOfIndentRequest>> tenantIdToServiceWrapperMap = getTenantIdToServiceWrapperMap(loiRequestList);

        List<LetterOfIndentRequest> enrichedServiceWrappers = new ArrayList<>();

        for (String tenantId : tenantIdToServiceWrapperMap.keySet()) {

            List<String> loiNumbers = new ArrayList<>();

            List<LetterOfIndentRequest> tenantSpecificWrappers = tenantIdToServiceWrapperMap.get(tenantId);

            tenantSpecificWrappers.forEach(loiWrapper -> {
                loiNumbers.add(loiWrapper.getLetterOfIndent().getLetterOfIndentNumber());
            });

            RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

            StringBuilder searchUrl = getprocessInstanceSearchURL(tenantId, StringUtils.join(loiNumbers, ','));
            Object result = repository.fetchResult(searchUrl, requestInfoWrapper);


            ProcessInstanceResponse processInstanceResponse = null;
            try {
                processInstanceResponse = mapper.convertValue(result, ProcessInstanceResponse.class);
            } catch (IllegalArgumentException e) {
                throw new CustomException("PARSING ERROR", "Failed to parse response of workflow processInstance search");
            }

            if (CollectionUtils.isEmpty(processInstanceResponse.getProcessInstances()) || processInstanceResponse.getProcessInstances().size() != loiNumbers.size())
                throw new CustomException("WORKFLOW_NOT_FOUND", "The workflow object is not found");

            Map<String, LetterOfIndentRequestWorkflow> businessIdToWorkflow = getWorkflow(processInstanceResponse.getProcessInstances());

            tenantSpecificWrappers.forEach(loiWrapper -> {
                loiWrapper.setWorkflow(businessIdToWorkflow.get(loiWrapper.getLetterOfIndent().getLetterOfIndentNumber()));
            });

            enrichedServiceWrappers.addAll(tenantSpecificWrappers);
        }

        return enrichedServiceWrappers;

    }

    private Map<String, List<LetterOfIndentRequest>> getTenantIdToServiceWrapperMap(List<LetterOfIndentRequest> loiRequestList) {
        Map<String, List<LetterOfIndentRequest>> resultMap = new HashMap<>();
        for (LetterOfIndentRequest loiRequest : loiRequestList) {
            if (resultMap.containsKey(loiRequest.getLetterOfIndent().getTenantId())) {
                resultMap.get(loiRequest.getLetterOfIndent().getTenantId()).add(loiRequest);
            } else {
                List<LetterOfIndentRequest> loiWrapperList = new ArrayList<>();
                loiWrapperList.add(loiRequest);
                resultMap.put(loiRequest.getLetterOfIndent().getTenantId(), loiWrapperList);
            }
        }
        return resultMap;
    }

    /*
     * Enriches ProcessInstance Object for workflow
     *
     * @param request
     */

    private ProcessInstance getProcessInstanceForLOI(LetterOfIndentRequest request) {

        LetterOfIndent letterOfIndent = request.getLetterOfIndent();
        LetterOfIndentRequestWorkflow workflow = request.getWorkflow();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setBusinessId(letterOfIndent.getLetterOfIndentNumber());
        processInstance.setAction(request.getWorkflow().getAction());
        processInstance.setModuleName(serviceConfiguration.getWorkflowLOIModuleName());
        processInstance.setTenantId(letterOfIndent.getTenantId());
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

    /*
     * @param processInstances
     */

    public Map<String, LetterOfIndentRequestWorkflow> getWorkflow(List<ProcessInstance> processInstances) {

        Map<String, LetterOfIndentRequestWorkflow> businessIdToWorkflow = new HashMap<>();

        processInstances.forEach(processInstance -> {
            List<String> userIds = null;

            if (!CollectionUtils.isEmpty(processInstance.getAssignes())) {
                userIds = processInstance.getAssignes().stream().map(User::getUuid).collect(Collectors.toList());
            }

            LetterOfIndentRequestWorkflow workflow = LetterOfIndentRequestWorkflow.builder()
                    .action(processInstance.getAction())
                    .assignees(userIds)
                    .comment(processInstance.getComment())
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

        ProcessInstanceResponse response = null;
        StringBuilder url = new StringBuilder(serviceConfiguration.getWorkflowHost().concat(serviceConfiguration.getWorkflowTransitionPath()));
        Object optional = repository.fetchResult(url, workflowReq);
        response = mapper.convertValue(optional, ProcessInstanceResponse.class);
        return response.getProcessInstances().get(0).getState();
    }


    public StringBuilder getprocessInstanceSearchURL(String tenantId, String loiNumber) {

        StringBuilder url = new StringBuilder(serviceConfiguration.getWorkflowHost());
        url.append(serviceConfiguration.getWfProcessInstanceSearchPath());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&businessIds=");
        url.append(loiNumber);
        return url;

    }

}

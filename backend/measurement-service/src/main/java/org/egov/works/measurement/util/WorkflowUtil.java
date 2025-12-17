package org.egov.works.measurement.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.common.contract.workflow.*;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.MBServiceConfiguration;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.web.models.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.measurement.config.ErrorConfiguration.PROCESS_INSTANCES_NOT_FOUND_CODE;
import static org.egov.works.measurement.config.ErrorConfiguration.PROCESS_INSTANCES_NOT_FOUND_MSG;
import static org.egov.works.measurement.config.ServiceConstants.*;

@Service
public class WorkflowUtil {

    private final ServiceRequestRepository repository;

    private final ObjectMapper mapper;

    private final MBServiceConfiguration configs;

    @Autowired
    public WorkflowUtil(ServiceRequestRepository repository, ObjectMapper mapper, MBServiceConfiguration configs) {
        this.repository = repository;
        this.mapper = mapper;
        this.configs = configs;
    }


    public List<String> getActions (RequestInfo requestInfo, String tenantId, String businessId) {
        StringBuilder url = getSearchURLWithParamsForActionSearch(tenantId, businessId);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object result = repository.fetchResult(url, requestInfoWrapper);
        ProcessInstanceResponse response = null;
        try {
            response = mapper.convertValue(result, ProcessInstanceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException(PARSING_ERROR, FAILED_TO_PARSE_BUSINESS_SERVICE_SEARCH);
        }

        if (CollectionUtils.isEmpty(response.getProcessInstances())) {
            throw new CustomException(PROCESS_INSTANCES_NOT_FOUND_CODE, PROCESS_INSTANCES_NOT_FOUND_MSG + businessId);
        }

        return response.getProcessInstances().get(0).getState().getActions().stream().map(Action::getAction).collect(Collectors.toList());
    }

    /**
     * Searches the BussinessService corresponding to the businessServiceCode
     * Returns applicable BussinessService for the given parameters
     *
     * @param requestInfo
     * @param tenantId
     * @param businessServiceCode
     * @return
     */
    public BusinessService getBusinessService(RequestInfo requestInfo, String tenantId, String businessServiceCode) {

        StringBuilder url = getSearchURLWithParams(tenantId, businessServiceCode);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object result = repository.fetchResult(url, requestInfoWrapper);
        BusinessServiceResponse response = null;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException(PARSING_ERROR, FAILED_TO_PARSE_BUSINESS_SERVICE_SEARCH);
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException(BUSINESS_SERVICE_NOT_FOUND, THE_BUSINESS_SERVICE + businessServiceCode + NOT_FOUND);

        return response.getBusinessServices().get(0);
    }

    /**
     * Calls the workflow service with the given action and updates the status
     * Returns the updated status of the application
     *
     * @param requestInfo
     * @param tenantId
     * @param businessId
     * @param businessServiceCode
     * @param workflow
     * @param wfModuleName
     * @return
     */
    public String updateWorkflowStatus(RequestInfo requestInfo, String tenantId, String businessId, String businessServiceCode, Workflow workflow, String wfModuleName, MeasurementService measurementService) {
        if (workflow.getAction().equals(SAVE_AS_DRAFT_ACTION) && (workflow.getAssignes() == null ||  workflow.getAssignes().isEmpty())){
            workflow.setAssignes(Collections.singletonList(requestInfo.getUserInfo().getUuid()));
        }
        ProcessInstance processInstance = getProcessInstanceForWorkflow(requestInfo, tenantId, businessId, businessServiceCode, workflow, wfModuleName);
        ProcessInstanceRequest workflowRequest = new ProcessInstanceRequest(requestInfo, Collections.singletonList(processInstance));
        State state = callWorkFlow(workflowRequest);
        measurementService.setProcessInstance(processInstance);
        return state.getApplicationStatus();
    }

    /**
     * Creates url for search based on given tenantId and businessServices
     *
     * @param tenantId
     * @param businessService
     * @return
     */
    private StringBuilder getSearchURLWithParams(String tenantId, String businessService) {
        StringBuilder url = new StringBuilder(configs.getWfHost());
        url.append(configs.getWfBusinessServiceSearchPath());
        url.append(TENANTID);
        url.append(tenantId);
        url.append(BUSINESS_SERVICES);
        url.append(businessService);
        return url;
    }

    /**
     * Creates url for process search
     * @param tenantId
     * @param businessId
     * @return
     */
    private StringBuilder getSearchURLWithParamsForActionSearch(String tenantId, String businessId) {
        StringBuilder url = new StringBuilder(configs.getWfHost());
        url.append(configs.getWfProcessInstanceSearchPath());
        url.append(TENANTID);
        url.append(tenantId);
        url.append(BUSINESS_IDS);
        url.append(businessId);
        return url;
    }

    /**
     * Enriches ProcessInstance Object for Workflow
     *
     * @param requestInfo
     * @param tenantId
     * @param businessId
     * @param businessServiceCode
     * @param workflow
     * @param wfModuleName
     * @return
     */
    public ProcessInstance getProcessInstanceForWorkflow(RequestInfo requestInfo, String tenantId,
                                                         String businessId, String businessServiceCode, Workflow workflow, String wfModuleName) {

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setBusinessId(businessId);
        processInstance.setAction(workflow.getAction());
        processInstance.setModuleName(wfModuleName);
        processInstance.setTenantId(tenantId);
        processInstance.setBusinessService(getBusinessService(requestInfo, tenantId, businessServiceCode).getBusinessService());
        processInstance.setDocuments(workflow.getDocuments());
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

        return processInstance;
    }

    /**
     * Gets the workflow corresponding to the processInstance
     *
     * @param processInstances
     * @return
     */
    public Map<String, Workflow> getWorkflow(List<ProcessInstance> processInstances) {

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
                    .documents(processInstance.getDocuments())
                    .build();

            businessIdToWorkflow.put(processInstance.getBusinessId(), workflow);
        });

        return businessIdToWorkflow;
    }

    /**
     * Method to take the ProcessInstanceRequest as parameter and set resultant status
     *
     * @param workflowReq
     * @return
     */
    private State callWorkFlow(ProcessInstanceRequest workflowReq) {
        ProcessInstanceResponse response = null;
        StringBuilder url = new StringBuilder(configs.getWfHost().concat(configs.getWfTransitionPath()));
        Object optional = repository.fetchResult(url, workflowReq);
        response = mapper.convertValue(optional, ProcessInstanceResponse.class);
        return response.getProcessInstances().get(0).getState();
    }
}

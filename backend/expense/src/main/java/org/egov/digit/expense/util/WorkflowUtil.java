package org.egov.digit.expense.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import digit.models.coremodels.BusinessService;
import digit.models.coremodels.BusinessServiceResponse;
import digit.models.coremodels.ProcessInstance;
import digit.models.coremodels.ProcessInstanceRequest;
import digit.models.coremodels.ProcessInstanceResponse;
import digit.models.coremodels.RequestInfoWrapper;
import digit.models.coremodels.State;
import digit.models.coremodels.Workflow;

@Service
public class WorkflowUtil {

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Configuration configs;



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
    * @param workflowReq
    * @return
    */
    public State callWorkFlow(ProcessInstanceRequest workflowRequest) {
    	
        ProcessInstanceResponse response;
        StringBuilder url = new StringBuilder(configs.getWfHost().concat(configs.getWfTransitionPath()));
        Object optional = repository.fetchResult(url, workflowRequest);
        response = mapper.convertValue(optional, ProcessInstanceResponse.class);
        return response.getProcessInstances().get(0).getState();
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
    			.documents(workflowFromRequest.getVerificationDocuments())
    			.moduleName(configs.getExpenseWorkflowModuleName())
    			.businessService(bill.getBusinessService())
    			.comment(workflowFromRequest.getComments())
    			.action(workflowFromRequest.getAction())
    			.businessId(bill.getBillNumber())
    			.tenantId(bill.getTenantId())
    			.assignes(assignes) 
    			.build();
    	
    	return ProcessInstanceRequest.builder()
    			.processInstances(Arrays.asList(processInstance))
    			.requestInfo(billRequest.getRequestInfo())
    			.build();
    }
}
package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.project.ProjectResponse;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ProjectUtil {

    private final ExpenseCalculatorConfiguration serviceConfiguration;

    private final ServiceRequestRepository requestRepository;

    private final ObjectMapper mapper;

    public static final String TENANT_ID = "tenantId";
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String AMPERSAND = "&";
    public static final String DEFAULT_LIMIT = "20";
    public static final String DEFAULT_OFFSET = "0";

    public static final String PROJECTS = "Projects";
    public static final String REQUEST_INFO = "RequestInfo";
    public static final String ID = "id";
    public static final String PROJECT_NUMBER = "projectNumber";
    public static final String PROJECT_NAME = "name";
    public static final String BOUNDARY_CODE = "boundary";

    public static final String EQUAL_TO = "=";

    @Autowired
    public ProjectUtil(ExpenseCalculatorConfiguration serviceConfiguration, ServiceRequestRepository requestRepository, ObjectMapper mapper) {
        this.serviceConfiguration = serviceConfiguration;
        this.requestRepository = requestRepository;
        this.mapper = mapper;
    }

    /**
     * Get the project details using project id from project service
     *
     * @param request
     * @return
     */
    public Object getProjectDetails(CalculatorSearchRequest request) {
        log.info("ProjectUtil::getProjectDetails");
        RequestInfo requestInfo = request.getRequestInfo();
        String tenantId = request.getSearchCriteria().getTenantId();
        String projectName = request.getSearchCriteria().getProjectName();
        String boundaryCode = request.getSearchCriteria().getBoundary();
        
        StringBuilder uriBuilder = getProjectUrl();

        //added the url param
        uriBuilder.append("?").append(TENANT_ID).append(EQUAL_TO).append(tenantId)
                .append(AMPERSAND)
                .append(OFFSET).append(EQUAL_TO).append(DEFAULT_OFFSET)
                .append(AMPERSAND)
                .append(LIMIT).append(EQUAL_TO).append(DEFAULT_LIMIT);


        //created the project search request body
        ObjectNode projectSearchReqNode = mapper.createObjectNode();
        ArrayNode projectArrayNode = mapper.createArrayNode();

	        ObjectNode projectObjNode = mapper.createObjectNode();
	        projectObjNode.put(TENANT_ID, tenantId);
	        if(projectName!=null)
	        	projectObjNode.put(PROJECT_NAME, projectName);
	        if(boundaryCode!=null) {
		        ObjectNode addressObjNode = mapper.createObjectNode();
	        	addressObjNode.put(BOUNDARY_CODE, boundaryCode);
	        	projectObjNode.putPOJO("address", addressObjNode);
	        }
	        projectArrayNode.add(projectObjNode);

        projectSearchReqNode.putPOJO(REQUEST_INFO, requestInfo);
        projectSearchReqNode.putPOJO(PROJECTS, projectArrayNode);

        log.info("ProjectUtil::search project request -> {}",projectSearchReqNode);
        return requestRepository.fetchResult(uriBuilder, projectSearchReqNode);
    }

    private StringBuilder getProjectUrl() {
        StringBuilder uriBuilder = new StringBuilder();
        return (uriBuilder.append(serviceConfiguration.getProjectHost())
                .append(serviceConfiguration.getProjectSearchPath()));
    }

    public ProjectResponse getProjectDetails(RequestInfo requestInfo, String tenantId, String projectId) {
        log.info("ProjectUtil::getProjectDetails");

        StringBuilder uriBuilder = getProjectUrl();

        //added the url param
        uriBuilder.append("?").append(TENANT_ID).append(EQUAL_TO).append(tenantId)
                .append(AMPERSAND)
                .append(OFFSET).append(EQUAL_TO).append(DEFAULT_OFFSET)
                .append(AMPERSAND)
                .append(LIMIT).append(EQUAL_TO).append(DEFAULT_LIMIT);


        //created the project search request body
        ObjectNode projectSearchReqNode = mapper.createObjectNode();
        ArrayNode projectArrayNode = mapper.createArrayNode();

        ObjectNode projectObjNode = mapper.createObjectNode();
        projectObjNode.put(ID, projectId);
        projectObjNode.put(TENANT_ID, tenantId);

        projectArrayNode.add(projectObjNode);

        projectSearchReqNode.putPOJO(REQUEST_INFO, requestInfo);
        projectSearchReqNode.putPOJO(PROJECTS, projectArrayNode);

        log.info("ProjectUtil::search project request -> {}",projectSearchReqNode);

        ProjectResponse projectResponse = null;

        try {
            Object responseObj = requestRepository.fetchResult(uriBuilder, projectSearchReqNode);
            projectResponse = mapper.convertValue(responseObj, ProjectResponse.class);
        } catch (Exception e) {
            log.error("Exception while fetching from searcher: ", e);
        }
        return projectResponse;
    }
}

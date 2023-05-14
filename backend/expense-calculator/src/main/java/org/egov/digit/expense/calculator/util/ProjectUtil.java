package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ProjectUtil {

    @Autowired
    private ExpenseCalculatorConfiguration serviceConfiguration;

    @Autowired
    private ServiceRequestRepository requestRepository;

    @Autowired
    private ObjectMapper mapper;

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

    /**
     * Get the project details using project id from project service
     *
     * @param request
     * @return
     */
    public Object getProjectDetails(CalculatorSearchRequest request, List<String> projectNumbers) {
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

//        for(String projectNumber: projectNumbers)
//        {
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
        //}

        projectSearchReqNode.putPOJO(REQUEST_INFO, requestInfo);
        projectSearchReqNode.putPOJO(PROJECTS, projectArrayNode);

        log.info("ProjectUtil::search project request -> {}",projectSearchReqNode);
        Object projectRes = requestRepository.fetchResult(uriBuilder, projectSearchReqNode);

        return projectRes;
    }

    private StringBuilder getProjectUrl() {
        StringBuilder uriBuilder = new StringBuilder();
        return (uriBuilder.append(serviceConfiguration.getProjectHost())
                .append(serviceConfiguration.getProjectSearchPath()));
    }

}

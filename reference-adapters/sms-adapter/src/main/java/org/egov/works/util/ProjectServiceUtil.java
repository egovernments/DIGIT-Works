package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.NotificationServiceConfiguration;
import org.egov.works.models.Estimate;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.config.Constants.*;

@Component
@Slf4j
public class ProjectServiceUtil {

    public static final String TENANT_ID = "tenantId";
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String AMPERSAND = "&";
    public static final String DEFAULT_LIMIT = "1";
    public static final String DEFAULT_OFFSET = "0";
    public static final String PROJECTS = "Projects";
    public static final String REQUEST_INFO = "RequestInfo";
    public static final String ID = "id";
    public static final String EQUAL_TO = "=";
    @Autowired
    private NotificationServiceConfiguration serviceConfiguration;
    @Autowired
    private ServiceRequestRepository requestRepository;
    @Autowired
    private ObjectMapper mapper;

    /**
     * Get the project details using project id from project service
     *
     * @param requestInfo
     * @param estimate
     * @return
     */
    public Map<String,String> getProjectDetails(RequestInfo requestInfo, Estimate estimate) {
        log.info("ProjectUtil::getProjectDetails");
        String projectId = estimate.getProjectId();
        String tenantId = estimate.getTenantId();

        StringBuilder uriBuilder = getProjectUrl(tenantId);

        //created the project search request body
        ObjectNode projectSearchReqNode = mapper.createObjectNode();
        ArrayNode projectArrayNode = mapper.createArrayNode();

        ObjectNode projectObjNode = mapper.createObjectNode();
        projectObjNode.put(ID, projectId);
        projectObjNode.put(TENANT_ID, tenantId);

        projectArrayNode.add(projectObjNode);

        projectSearchReqNode.putPOJO(REQUEST_INFO, requestInfo);
        projectSearchReqNode.putPOJO(PROJECTS, projectArrayNode);

        log.info("ProjectUtil::search project request -> {}", projectSearchReqNode);
        Object projectRes = requestRepository.fetchResult(uriBuilder, projectSearchReqNode);

        Map<String, String> projectDetails = new HashMap<>();
        List<String> projectNumber = null;
        List<String> projectNames = null;
        List<String> boundaries = null;
        List<String> boundaryTypes=null;

        try {
            projectNumber = JsonPath.read(projectRes, PROJECT_NUMBER_CODE);
            projectNames = JsonPath.read(projectRes, PROJECT_NAME_CODE);
            boundaries = JsonPath.read(projectRes,PROJECT_BOUNDARY_CODE);
            boundaryTypes = JsonPath.read(projectRes,PROJECT_BOUNDARY_TYPE_CODE);

        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse HRMS response");
        }

        projectDetails.put("projectName", projectNames.get(0));
        projectDetails.put("boundary", boundaries.get(0));
        projectDetails.put("boundaryType", boundaryTypes.get(0));
        projectDetails.put("projectNumber", projectNumber.get(0));

        return projectDetails;
    }

    private StringBuilder getProjectUrl(String tenantId) {
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(serviceConfiguration.getWorksProjectManagementSystemHost())
                .append(serviceConfiguration.getWorksProjectManagementSystemPath())
                .append("?").append(TENANT_ID).append(EQUAL_TO).append(tenantId)
                .append(AMPERSAND)
                .append(OFFSET).append(EQUAL_TO).append(DEFAULT_OFFSET)
                .append(AMPERSAND)
                .append(LIMIT).append(EQUAL_TO).append(DEFAULT_LIMIT);

        return uriBuilder;
    }

}

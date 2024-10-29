package org.egov.works.mukta.adapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.project.ProjectResponse;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ProjectUtil {

    private final MuktaAdaptorConfig serviceConfiguration;

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
    public ProjectUtil(MuktaAdaptorConfig serviceConfiguration, ServiceRequestRepository requestRepository, ObjectMapper mapper) {
        this.serviceConfiguration = serviceConfiguration;
        this.requestRepository = requestRepository;
        this.mapper = mapper;
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

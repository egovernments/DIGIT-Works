package org.egov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.egov.util.EstimateServiceConstant.EQUAL_TO;

@Component
@Slf4j
public class ProjectUtil {

    @Autowired
    private EstimateServiceConfiguration serviceConfiguration;

    @Autowired
    private ServiceRequestRepository requestRepository;

    @Autowired
    private ObjectMapper mapper;

    public static final String TENANT_ID = "tenantId";
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String AMPERSAND = "&";
    public static final String DEFAULT_LIMIT = "1";
    public static final String DEFAULT_OFFSET = "0";

    public static final String PROJECTS = "Projects";
    public static final String REQUEST_INFO = "RequestInfo";
    public static final String ID = "id";

    /**
     * Get the project details using project id from project service
     *
     * @param estimateRequest
     * @return
     */
    public Object getProjectDetails(EstimateRequest estimateRequest) {
        log.info("ProjectUtil::getProjectDetails");
        RequestInfo requestInfo = estimateRequest.getRequestInfo();
        String projectId = estimateRequest.getEstimate().getProjectId();
        String tenantId = estimateRequest.getEstimate().getTenantId();

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
        Object projectRes = requestRepository.fetchResult(uriBuilder, projectSearchReqNode);

        return projectRes;
    }

    private StringBuilder getProjectUrl() {
        StringBuilder uriBuilder = new StringBuilder();
        return (uriBuilder.append(serviceConfiguration.getWorksProjectManagementSystemHost())
                .append(serviceConfiguration.getWorksProjectManagementSystemPath()));
    }

}

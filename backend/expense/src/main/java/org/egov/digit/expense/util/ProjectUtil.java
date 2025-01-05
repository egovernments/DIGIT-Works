package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.project.*;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
@Slf4j
public class ProjectUtil {

    private final Configuration serviceConfiguration;

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
    public ProjectUtil(Configuration serviceConfiguration, ServiceRequestRepository requestRepository, ObjectMapper mapper) {
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

    private StringBuilder getProjectUrl() {
        StringBuilder uriBuilder = new StringBuilder();
        return (uriBuilder.append(serviceConfiguration.getProjectHost())
                .append(serviceConfiguration.getProjectSearchPath()));
    }

    public ProjectResponse getProjectDetails(BillSearchRequest billSearchRequest) {
        log.info("ProjectUtil::getProjectDetails");

        StringBuilder uriBuilder = getProjectUrl();

        //added the url param
        uriBuilder.append("?").append(TENANT_ID).append(EQUAL_TO).append(billSearchRequest.getBillCriteria().getTenantId())
                .append(AMPERSAND)
                .append("isAncestorProjectId").append(EQUAL_TO).append("true")
                .append(AMPERSAND)
                .append("includeDescendants").append(EQUAL_TO).append("false")
                .append(AMPERSAND)
                .append(OFFSET).append(EQUAL_TO).append(DEFAULT_OFFSET)
                .append(AMPERSAND)
                .append(LIMIT).append(EQUAL_TO).append(DEFAULT_LIMIT);

        ProjectRequest projectRequest = ProjectRequest.builder()
                .requestInfo(billSearchRequest.getRequestInfo())
                .projects(Collections.singletonList(Project.builder().id(billSearchRequest.getBillCriteria().getReferenceIds().stream().toList().get(0))
                        .tenantId(billSearchRequest.getBillCriteria().getTenantId())
                        .address(Address.builder().boundary(billSearchRequest.getBillCriteria().getLocalityCode()).build())
                        .build()))
                .build();

        ProjectResponse projectResponse = null;

        try {
            Object responseObj = requestRepository.fetchResult(uriBuilder, projectRequest);
            projectResponse = mapper.convertValue(responseObj, ProjectResponse.class);
        } catch (Exception e) {
            log.error("Exception while fetching from searcher: ", e);
        }
        return projectResponse;
    }
}

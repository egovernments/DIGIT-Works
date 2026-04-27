package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ProjectStaffUtil {

    private final Configuration config;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ObjectMapper mapper;

    @Autowired
    public ProjectStaffUtil(Configuration config, ServiceRequestRepository serviceRequestRepository, ObjectMapper mapper) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
        this.mapper = mapper;
    }

    /**
     * Returns the subset of staffIds that have an active project staff record
     * for any of the given projectIds.
     */
    public Set<String> filterByProjectStaff(RequestInfo requestInfo, String tenantId,
                                             List<String> staffIds, List<String> projectIds) {
        try {
            String uri = UriComponentsBuilder
                    .fromHttpUrl(config.getProjectServiceHost() + config.getProjectStaffEndpoint())
                    .queryParam("tenantId", tenantId)
                    .queryParam("limit", config.getProjectStaffSearchLimit())
                    .queryParam("offset", 0)
                    .toUriString();

            ObjectNode body = mapper.createObjectNode();
            body.putPOJO("RequestInfo", requestInfo);
            ObjectNode staffNode = mapper.createObjectNode();
            staffNode.putPOJO("staffId", staffIds);
            staffNode.putPOJO("projectId", projectIds);
            body.set("ProjectStaff", staffNode);

            Object response = serviceRequestRepository.fetchResult(new StringBuilder(uri), body);
            List<String> matched = JsonPath.read(response, "$.ProjectStaff[*].userId");
            return new HashSet<>(matched);
        } catch (Exception e) {
            log.warn("ProjectStaffUtil: failed to filter project staff tenantId={}: {}", tenantId, e.getMessage());
            return Collections.emptySet();
        }
    }
}

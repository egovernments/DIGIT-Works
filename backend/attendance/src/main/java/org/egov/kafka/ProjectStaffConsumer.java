package org.egov.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.core.Role;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.IndividualSearch;
import org.egov.common.models.project.ProjectStaff;
import org.egov.common.models.project.ProjectStaffBulkRequest;
import org.egov.common.models.project.ProjectStaffRequest;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.util.IndividualServiceUtil;
import org.egov.util.ProjectStaffUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "project.staff.attendance.register.enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class ProjectStaffConsumer {

    private final ObjectMapper objectMapper;

    private final ProjectStaffUtil projectStaffUtil;

    private final IndividualServiceUtil individualServiceUtil;

    private final AttendanceServiceConfiguration config;

    @Autowired
    public ProjectStaffConsumer(ObjectMapper objectMapper, ProjectStaffUtil projectStaffUtil, IndividualServiceUtil individualServiceUtil, AttendanceServiceConfiguration config) {
        this.objectMapper = objectMapper;
        this.projectStaffUtil = projectStaffUtil;
        this.individualServiceUtil = individualServiceUtil;
        this.config = config;
    }

    /**
     * This Kafka listener subscribes to topics matching the pattern:
     * [optional tenant prefix] + "project-staff-attendance-health-topic"
     *
     * The tenant prefix is configured in `attendance.kafka.tenant.id.pattern` as a regex,
     * e.g., "^(kebbi-|kano-)", allowing the listener to consume from:
     * - project-staff-attendance-health-topic
     * - kebbi-project-staff-attendance-health-topic
     * - kano-project-staff-attendance-health-topic
     *
     * `{0,1}` makes the tenant prefix optional, so the listener supports both
     * tenant-specific and global topics.
     */

    @KafkaListener(topicPattern = "(${attendance.kafka.tenant.id.pattern}){0,1}${project.staff.attendance.topic}")
    public void bulkStaffCreate(Map<String, Object> consumerRecord,
                                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Received Project Staff Bulk Create Message for topic {}", topic);
            // Convert the received Kafka message into a ProjectStaffBulkRequest object
            ProjectStaffBulkRequest request = objectMapper.convertValue(consumerRecord, ProjectStaffBulkRequest.class);

            // Iterate over the ProjectStaff objects in the request
            for (ProjectStaff projectStaff : request.getProjectStaff()) {
                try {
                    RequestInfo requestInfo = request.getRequestInfo();

                    List<String> staffUserUuids = Collections.singletonList(projectStaff.getUserId());
                    String tenantId = projectStaff.getTenantId();

                    // Search for the individual details using the user UUID
                    IndividualSearch individualSearch = IndividualSearch.builder().userUuid(staffUserUuids).build();
                    List<Individual> individualList = individualServiceUtil.getIndividualDetailsFromSearchCriteria(individualSearch, requestInfo, tenantId);

                    if (individualList.isEmpty())
                        throw new CustomException("INVALID_STAFF_ID", "No Individual found for the given staff Uuid - " + staffUserUuids);
                    Individual individual = individualList.get(0);

                    List<Role> roleList = individual.getUserDetails().getRoles();
                    List<String> roleCodeList = roleList.stream()
                            .map(Role::getCode)
                            .collect(Collectors.toList());

                    // Check if the individual has any supervisor roles
                    boolean matchFoundForSupervisorRoles = roleCodeList.stream()
                            .anyMatch(config.getProjectSupervisorRoles()::contains);

                    // Check if the individual has any attendee roles
                    boolean matchFoundForAttendeeRoles = roleCodeList.stream()
                            .anyMatch(config.getProjectAttendeeRoles()::contains);

                    // If the individual has supervisor roles, create a registry for supervisor
                    if (matchFoundForSupervisorRoles)
                        projectStaffUtil.createRegistryForSupervisor(projectStaff, requestInfo, individual);

                    // If the individual has attendee roles, enroll the attendee to register
                    if (matchFoundForAttendeeRoles)
                        projectStaffUtil.enrollAttendeetoRegister(projectStaff, requestInfo, individual);
                }
                catch (Exception e)
                {
                    log.error(e.toString());
                }
            }

        } catch (Exception exception) {
            log.error("error in project staff consumer bulk create", exception);
        }
    }


}

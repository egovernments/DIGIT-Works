package org.egov.digit.expense.calculator.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.attendance.AttendanceRegister;
import org.egov.works.services.common.models.attendance.AttendanceRegisterRequest;
import org.egov.works.services.common.models.attendance.AttendanceRegisterResponse;
import org.egov.works.services.common.models.musterroll.Status;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AttendanceUtil {

    private final ExpenseCalculatorConfiguration config;
    private final RestTemplate restTemplate;

    public AttendanceUtil(ExpenseCalculatorConfiguration config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }


    public List<AttendanceRegister> fetchAttendanceRegister(String referenceId, String tenantId, RequestInfo requestInfo,
                                                            String localityCode, boolean isChildrenRequired, Integer offset) {
        log.info("Fetching attendance register with tenantId::" + tenantId
                + " and offset::" + offset + " and batch size:: " +config.getRegisterBatchSize());

        StringBuilder uri = new StringBuilder();
        uri.append(config.getAttendanceLogHost()).append(config.getAttendanceRegisterEndpoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("tenantId", tenantId)
                .queryParam("referenceId", referenceId)
                .queryParam("isChildrenRequired", isChildrenRequired)
                .queryParam("status", Status.ACTIVE)
                .queryParam("offset", offset)
                .queryParam("limit", config.getRegisterBatchSize());

        // Only add localityCode if it's not null/blank
        // Attendance service validation requires BOTH referenceId AND localityCode or NEITHER
        if (localityCode != null && !localityCode.trim().isEmpty()) {
            uriBuilder.queryParam("localityCode", localityCode);
        }

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        AttendanceRegisterResponse attendanceRegisterResponse = null;

        try {
            attendanceRegisterResponse = restTemplate.postForObject(uriBuilder.toUriString(), requestInfoWrapper,
                    AttendanceRegisterResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("AttendanceUtil::Error thrown from attendance register service::"
                    + httpClientOrServerExc.getStatusCode());
            throw new CustomException("ATTENDANCE_REGISTER_SERVICE_EXCEPTION",
                    "Error thrown from attendance register service::" + httpClientOrServerExc.getStatusCode());
        }

        if (attendanceRegisterResponse == null || attendanceRegisterResponse.getAttendanceRegister() == null) {
            log.error("Error fetching registers::"
                    + referenceId);
            throw new CustomException("ERROR_FETCHING_ATTENDANCE_REGISTER",
                    "Error fetching attendance registers");
        }
        return attendanceRegisterResponse.getAttendanceRegister();
    }

    /**
     * Update reviewStatus of attendance registers for a project after period bill generation.
     * This marks the registers status based on billing progress.
     *
     * V2 Flow: Called after successful bill generation
     * - PENDINGFORAPPROVAL: For intermediate periods
     * - APPROVED: For the last period
     *
     * IMPORTANT: Permissions are validated in pre-validation phase before async processing.
     * This method assumes the user has already been validated to have permission on all registers.
     *
     * @param requestInfo Request info
     * @param projectId Project reference ID
     * @param tenantId Tenant ID
     * @param reviewStatus Review status to set
     * @param localityCode Locality code from original request (required for attendance search)
     */
    public void updateRegisterReviewStatus(RequestInfo requestInfo, String projectId,
                                          String tenantId, String reviewStatus, String localityCode) {
        log.info("Updating reviewStatus to '{}' for all registers of project: {}", reviewStatus, projectId);

        try {
            // Fetch all registers for this project
            List<AttendanceRegister> allRegisters = new ArrayList<>();
            int offset = 0;
            List<AttendanceRegister> batch;

            do {
                batch = fetchAttendanceRegister(projectId, tenantId, requestInfo,
                        localityCode, false, offset);

                if (!CollectionUtils.isEmpty(batch)) {
                    allRegisters.addAll(batch);
                    offset += batch.size();
                }
            } while (!CollectionUtils.isEmpty(batch) && batch.size() >= config.getRegisterBatchSize());

            if (CollectionUtils.isEmpty(allRegisters)) {
                log.warn("No registers found for project: {}. Skipping reviewStatus update.", projectId);
                return;
            }

            log.info("Found {} registers for project {}. Updating reviewStatus to '{}'",
                    allRegisters.size(), projectId, reviewStatus);

            // Update reviewStatus for each register
            for (AttendanceRegister register : allRegisters) {
                register.setReviewStatus(reviewStatus);
            }

            // Build update request
            AttendanceRegisterRequest updateRequest = AttendanceRegisterRequest.builder()
                    .requestInfo(requestInfo)
                    .attendanceRegister(allRegisters)
                    .build();

            // Call attendance update API
            StringBuilder uri = new StringBuilder();
            uri.append(config.getAttendanceLogHost())
               .append(config.getAttendanceRegisterUpdateEndpoint());

            log.info("Calling attendance service to update {} registers with reviewStatus='{}'",
                    allRegisters.size(), reviewStatus);

            AttendanceRegisterResponse response = restTemplate.postForObject(
                    uri.toString(),
                    updateRequest,
                    AttendanceRegisterResponse.class
            );

            if (response != null && !CollectionUtils.isEmpty(response.getAttendanceRegister())) {
                log.info("Successfully updated reviewStatus='{}' for {} registers of project {}",
                        reviewStatus, response.getAttendanceRegister().size(), projectId);
            } else {
                log.warn("Update response is empty for project {}", projectId);
            }

        } catch (HttpClientErrorException | HttpServerErrorException httpExc) {
            log.error("Error calling attendance service to update reviewStatus for project {}: {}",
                    projectId, httpExc.getStatusCode(), httpExc);
            throw new CustomException("ATTENDANCE_UPDATE_ERROR",
                    "Error updating attendance register reviewStatus: " + httpExc.getStatusCode());
        } catch (Exception e) {
            log.error("Unexpected error updating reviewStatus for project {}: {}",
                    projectId, e.getMessage(), e);
            throw new CustomException("ATTENDANCE_UPDATE_ERROR",
                    "Unexpected error updating attendance register reviewStatus: " + e.getMessage());
        }
    }
}

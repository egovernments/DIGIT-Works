package org.egov.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.models.AttendanceAttendeeSearchCriteria;
import org.egov.models.AttendanceLogSearchCriteria;
import org.egov.models.AttendanceRegisterSearchCriteria;
import org.egov.models.AttendanceStaffSearchCriteria;
import org.egov.repository.AttendanceLogRepository;
import org.egov.repository.AttendeeRepository;
import org.egov.repository.RegisterRepository;
import org.egov.repository.StaffRepository;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AttendanceLogServiceValidator {

    @Autowired
    private StaffRepository attendanceStaffRepository;

    @Autowired
    private RegisterRepository attendanceRegisterRepository;

    @Autowired
    private AttendeeRepository attendanceAttendeeRepository;

    @Autowired
    private AttendanceLogRepository attendanceLogRepository;

    @Autowired
    private AttendanceServiceConfiguration config;

    public void validateCreateAttendanceLogRequest(AttendanceLogRequest attendanceLogRequest) {
        validateAttendanceLogRequest(attendanceLogRequest);

        // Verify the Logged-in user is associated to the given register.
        validateLoggedInUser(attendanceLogRequest);

        // Verify if given tenantId is associated with given register
        validateTenantIdAssociationWithRegisterId(attendanceLogRequest);

        // Verify if individuals are part of the given register and individuals were active during given attendance log time.
        validateAttendees(attendanceLogRequest);

        // Verify provided documentIds are valid.
        validateDocumentIds(attendanceLogRequest);
    }

    public void validateUpdateAttendanceLogRequest(AttendanceLogRequest attendanceLogRequest) {
        validateAttendanceLogRequest(attendanceLogRequest);

        // Verify the Logged-in user is associated to the given register.
        validateLoggedInUser(attendanceLogRequest);

        // Verify provided log ids are present
        validateAttendanceLogIds(attendanceLogRequest);

        // Verify if individuals are part of the given register and individuals were active during given attendance log time.
        validateAttendees(attendanceLogRequest);

        // Verify provided documentIds are valid.
        validateDocumentIds(attendanceLogRequest);
    }

    private void validateTenantIdAssociationWithRegisterId(AttendanceLogRequest attendanceLogRequest) {
        String tenantId = attendanceLogRequest.getAttendance().get(0).getTenantId();
        String registerId = attendanceLogRequest.getAttendance().get(0).getRegisterId();
        validateTenantIdAssociationWithRegisterId(tenantId, registerId);
    }

    private void validateDocumentIds(AttendanceLogRequest attendanceLogRequest) {
        if ("TRUE".equalsIgnoreCase(config.getDocumentIdVerificationRequired())) {
            //TODO
            // For now throwing exception. Later implementation will be done
            throw new CustomException("SERVICE_UNAVAILABLE", "Service not integrated yet");
        }
    }

    private void validateAttendanceLogIds(AttendanceLogRequest attendanceLogRequest) {
        List<AttendanceLog> attendance = attendanceLogRequest.getAttendance();
        List<String> providedAttendanceLogIds = attendance.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
        List<AttendanceLog> fetchedAttendanceLogList = fetchAttendanceLogsByIds(providedAttendanceLogIds);
        Set<String> fetchedAttendanceLogIds = fetchedAttendanceLogList.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toSet());
        for (String providedAttendanceLogId : providedAttendanceLogIds) {
            if (!fetchedAttendanceLogIds.contains(providedAttendanceLogId)) {
                throw new CustomException("ATTENDANCE_LOG", "Attendance log is not present.");
            }
        }
    }

    private List<AttendanceLog> fetchAttendanceLogsByIds(List<String> ids) {
        //AttendanceLogSearchCriteria searchCriteria = AttendanceLogSearchCriteria.builder().ids(ids).status(Status.ACTIVE).build();
        AttendanceLogSearchCriteria searchCriteria = AttendanceLogSearchCriteria.builder().ids(ids).build();
        return attendanceLogRepository.getAttendanceLogs(searchCriteria);
    }

    private void validateAttendees(AttendanceLogRequest attendanceLogRequest) {

        /*
            For now, we are validating attendees on below basis.
            1. Verify that each attendee is associated to given register.
            2. Make the entry in attendance log table only if attendee was active during the given attendance time.

            Future:
            Once Individual service will be available will integrate it for further validation.
         */


        if ("TRUE".equalsIgnoreCase(config.getIndividualServiceIntegrationRequired())) {
            //TODO
            // For now throwing exception. Since individual service is under discussion.
            throw new CustomException("INTEGRATION_UNDERDEVELOPMENT", "Individual service integration is under development");
        }

        // Fetch all attendees for given register_id.
        String registerId = attendanceLogRequest.getAttendance().get(0).getRegisterId();
        List<IndividualEntry> fetchAttendanceAttendeeLst = fetchAllAttendeesEnrolledInARegister(registerId);

        // Convert the fetched Attendee List into a Map with individualId as key and corresponding Attendee list as value.
        Map<String, List<IndividualEntry>> attendanceAttendeeListMap = fetchAttendanceAttendeeLst
                .stream()
                .collect(Collectors.groupingBy(IndividualEntry::getIndividualId));

        // Identify unassociated(Attendees not associated with given register) and ineligible attendees
        identifyUnassociatedAndIneligibleAttendees(attendanceLogRequest, attendanceAttendeeListMap);
    }

    private void identifyUnassociatedAndIneligibleAttendees(AttendanceLogRequest attendanceLogRequest, Map<String, List<IndividualEntry>> attendanceAttendeeListMap) {
        List<String> unassociatedAttendees = new ArrayList<>();
        Set<String> eligibleAttendanceAttendeeIdSet = new HashSet<>();

        List<AttendanceLog> attendanceLogs = attendanceLogRequest.getAttendance();
        for (AttendanceLog attendanceLog : attendanceLogs) {
            String givenIndividualId = attendanceLog.getIndividualId();
            if (attendanceAttendeeListMap.containsKey(givenIndividualId)) {
                List<IndividualEntry> lst = attendanceAttendeeListMap.get(givenIndividualId);
                for (IndividualEntry attendee : lst) {
                    Instant instantAttendanceAttendeeLogTime = Instant.ofEpochMilli(attendanceLog.getTime().longValue());
                    Instant instantEnrollmentDate = Instant.ofEpochMilli(attendee.getEnrollmentDate().longValue());
                    if (attendee.getDenrollmentDate()==null) {
                        if (instantAttendanceAttendeeLogTime.compareTo(instantEnrollmentDate) >= 0) {
                            eligibleAttendanceAttendeeIdSet.add(attendee.getIndividualId());
                        }
                    } else {
                        Instant instantDenrollmentDate = Instant.ofEpochMilli(attendee.getDenrollmentDate().longValue());
                        if (instantAttendanceAttendeeLogTime.compareTo(instantEnrollmentDate) >= 0 && instantAttendanceAttendeeLogTime.compareTo(instantDenrollmentDate) <= 0) {
                            eligibleAttendanceAttendeeIdSet.add(attendee.getIndividualId());
                        }
                    }
                }
            } else {
                unassociatedAttendees.add(givenIndividualId);
            }

        }

        if (!unassociatedAttendees.isEmpty()) {
            throw new CustomException("UNENROLLED_ATTENDEES", "Attendees are not enrolled against given register");
        }

        //find ineligible list
        Set<String> inEligibleAttendanceAttendeeIdSet = new HashSet<>();
        for (AttendanceLog attendanceLog : attendanceLogs) {
            if (!eligibleAttendanceAttendeeIdSet.contains(attendanceLog.getIndividualId())) {
                inEligibleAttendanceAttendeeIdSet.add(attendanceLog.getIndividualId());
            }
        }

        if (!inEligibleAttendanceAttendeeIdSet.isEmpty()) {
            throw new CustomException("INELIGIBLE_ATTENDEES", "Attendees are ineligible for given date range");
        }
    }

    private List<IndividualEntry> fetchAllAttendeesEnrolledInARegister(String registerId) {
        AttendanceAttendeeSearchCriteria searchCriteria = AttendanceAttendeeSearchCriteria
                .builder()
                .registerId(registerId)
                .build();

        return attendanceAttendeeRepository.getAttendees(searchCriteria);
    }

    private void validateLoggedInUser(AttendanceLogRequest attendanceLogRequest) {
        /*
            For now, we are validating logged-in user on below basis.
            1. Logged-in user should be active user for provided register_id. Query eg_wms_attendance_staff table for same.

            Future
            Once Staff service will be available will integrate it for further validation.
         */

        if ("TRUE".equalsIgnoreCase(config.getStaffServiceIntegrationRequired())) {
            //TODO
            // For now throwing exception. Since Staff service is under development.
            throw new CustomException("INTEGRATION_UNDERDEVELOPMENT", "Staff service integration is under development");
        }

        String userUUID = attendanceLogRequest.getRequestInfo().getUserInfo().getUuid();
        String registerId = attendanceLogRequest.getAttendance().get(0).getRegisterId();
        validateLoggedInUser(userUUID, registerId);
    }

    public void validateSearchAttendanceLogRequest(RequestInfoWrapper requestInfoWrapper, AttendanceLogSearchCriteria searchCriteria) {

        // Verify given parameters
        validateSearchAttendanceLogParameters(requestInfoWrapper, searchCriteria);

        // Verify TenantId association with register
        validateTenantIdAssociationWithRegisterId(searchCriteria.getTenantId(), searchCriteria.getRegisterId());

        // Verify the Logged-in user is associated to the given register.
        validateLoggedInUser(requestInfoWrapper.getRequestInfo().getUserInfo().getUuid(), searchCriteria.getRegisterId());


    }

    private void validateSearchAttendanceLogParameters(RequestInfoWrapper requestInfoWrapper, AttendanceLogSearchCriteria searchCriteria) {
        if (searchCriteria == null || requestInfoWrapper == null) {
            throw new CustomException("ATTENDANCE_LOG_SEARCH_CRITERIA_REQUEST", "Attendance log search criteria request is mandatory");
        }

        Map<String, String> errorMap = new HashMap<>();

        validateRequestInfo(requestInfoWrapper.getRequestInfo(), errorMap);

        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "Tenant is mandatory");
        }
        if (StringUtils.isBlank(searchCriteria.getRegisterId())) {
            throw new CustomException("REGISTER_ID", "RegisterId is mandatory");
        }

        // Throw exception if required parameters are missing
        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);

        if (searchCriteria.getIndividualIds() != null && !searchCriteria.getIndividualIds().isEmpty() && searchCriteria.getIndividualIds().size() > 10) {
            throw new CustomException("INDIVIDUALIDS", "IndividualIds should be of max length 10.");
        }
    }

    private void validateTenantIdAssociationWithRegisterId(String tenantId, String registerId) {
        AttendanceRegisterSearchCriteria searchCriteria = AttendanceRegisterSearchCriteria
                .builder()
                .tenantId(tenantId)
                .ids(Collections.singletonList(registerId))
                .build();
        List<AttendanceRegister> attendanceRegister = attendanceRegisterRepository.getRegister(searchCriteria);
        if (attendanceRegister == null || attendanceRegister.isEmpty()) {
            throw new CustomException("INVALID_TENANTID", "TenantId is not associated with register");
        }
    }

    private void validateLoggedInUser(String userUUID, String registerId) {
        AttendanceStaffSearchCriteria searchCriteria = AttendanceStaffSearchCriteria
                .builder()
                .individualIds(Collections.singletonList(userUUID))
                .registerIds(Collections.singletonList(registerId))
                .build();
        List<StaffPermission> attendanceStaff = attendanceStaffRepository.getActiveStaff(searchCriteria);
        if (attendanceStaff == null || attendanceStaff.isEmpty()) {
            throw new CustomException("UNAUTHORISED_USER", "User is not authorised");
        }
    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }

    private void validateAttendanceLogRequest(AttendanceLogRequest attendanceLogRequest) {

        Map<String, String> errorMap = new HashMap<>();
        // Validate the Request Info object
        RequestInfo requestInfo = attendanceLogRequest.getRequestInfo();
        validateRequestInfo(requestInfo, errorMap);

        // Validate the Attendance Log parameters
        validateAttendanceLogParameters(attendanceLogRequest.getAttendance(), errorMap);

        // Throw exception if required parameters are missing
        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateAttendanceLogParameters(List<AttendanceLog> attendance, Map<String, String> errorMap) {
        if (attendance == null || attendance.isEmpty()) {
            throw new CustomException("ATTENDANCE", "Attendance array is mandatory");
        }

        for (AttendanceLog attendeeLog : attendance) {
            if (StringUtils.isBlank(attendeeLog.getTenantId())) {
                errorMap.put("ATTENDANCE.TENANTID", "TenantId is mandatory");
            }
            if (StringUtils.isBlank(attendeeLog.getRegisterId())) {
                errorMap.put("ATTENDANCE.REGISTERID", "Attendance registerid is mandatory");
            }
            if (attendeeLog.getIndividualId() == null) {
                errorMap.put("ATTENDANCE.INDIVIDUALID", "Attendance indidualid is mandatory");
            }
            if (StringUtils.isBlank(attendeeLog.getType())) {
                errorMap.put("ATTENDANCE.TYPE", "Attendance type is mandatory");
            }
            if (attendeeLog.getTime() == null) {
                errorMap.put("ATTENDANCE.TIME", "Attendance time is mandatory");
            }
        }
    }
}

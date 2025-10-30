package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.project.ProjectStaff;
import org.egov.common.models.project.ProjectStaffSearch;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.repository.RegisterRepository;
import org.egov.service.StaffService;
import org.egov.tracer.model.CustomException;
import org.egov.util.HRMSUtil;
import org.egov.util.IndividualServiceUtil;
import org.egov.util.MDMSUtils;
import org.egov.util.ProjectStaffUtil;
import org.egov.web.models.*;
import org.egov.web.models.Hrms.Assignment;
import org.egov.web.models.Hrms.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.util.AttendanceServiceConstants.*;

@Component
@Slf4j
public class AttendeeServiceValidator {

    private final MDMSUtils mdmsUtils;

    private final IndividualServiceUtil individualServiceUtil;

    private final HRMSUtil hrmsUtil;
    private final StaffService staffService;

    private final AttendanceServiceConfiguration config;


    @Autowired
    public AttendeeServiceValidator(MDMSUtils mdmsUtils, IndividualServiceUtil individualServiceUtil, HRMSUtil hrmsUtil, StaffService staffService, AttendanceServiceConfiguration config) {
        this.mdmsUtils = mdmsUtils;
        this.individualServiceUtil = individualServiceUtil;
        this.hrmsUtil = hrmsUtil;
        this.staffService = staffService;
        this.config = config;
    }

    /**
     * Validates the parameters of the AttendeeUpdateTagRequest object.
     *
     * @param attendeeUpdateTagRequest The request object containing the list of attendees to be updated.
     */
    public void validateAttendeeUpdateTagRequestParameters(AttendeeUpdateTagRequest attendeeUpdateTagRequest) {
        List<IndividualEntry> attendeeList = attendeeUpdateTagRequest.getAttendees();
        Map<String, String> errorMap = new HashMap<>();

        if (attendeeList == null || attendeeList.isEmpty()) {
            log.error(ERROR_MSG_EMPTY_ATTENDEE_LIST);
            throw new CustomException(ERROR_KEY_ATTENDEE, ERROR_MSG_EMPTY_ATTENDEE_LIST);
        }

        String tenantId = attendeeList.get(0).getTenantId();
        for (IndividualEntry attendee : attendeeList) {
            if (StringUtils.isBlank(attendee.getId())) {
                log.error(ERROR_MSG_ID_MANDATORY);
                errorMap.put(ERROR_KEY_ID, ERROR_MSG_ID_MANDATORY);
            }

            if (StringUtils.isBlank(attendee.getTenantId())) {
                log.error(ERROR_MSG_TENANT_ID_MANDATORY);
                errorMap.put(ERROR_KEY_TENANT_ID, ERROR_MSG_TENANT_ID_MANDATORY);
            }

            if (StringUtils.isBlank(attendee.getTag())) {
                log.error(ERROR_MSG_TAG_MANDATORY);
                errorMap.put(ERROR_KEY_TAG, ERROR_MSG_TAG_MANDATORY);
            }
        }

        if (!errorMap.isEmpty()) {
            log.error("Attendee request validation failed");
            throw new CustomException(errorMap);
        }

        validateTenantIds(attendeeUpdateTagRequest, tenantId);
        log.info("validating tenant id from MDMS and Request info");
        validateMDMSAndRequestInfoForUpdateTagAttendee(attendeeUpdateTagRequest);
    }

    /**
     * Validates that all requested attendees exist in the database records.
     *
     * @param attendees   The list of attendees requested for validation.
     * @param dbRecords   The list of attendees fetched from the database.
     * @throws CustomException if any requested attendee is not found in the database records.
     */
    public void validateAllAttendeesExist(List<IndividualEntry> attendees, List<IndividualEntry> dbRecords) {
        Set<String> requestedIds = attendees.stream()
                .map(IndividualEntry::getId)
                .collect(Collectors.toSet());

        Set<String> foundIds = dbRecords.stream()
                .map(IndividualEntry::getId)
                .collect(Collectors.toSet());

        Set<String> missingIds = new HashSet<>(requestedIds);
        missingIds.removeAll(foundIds);

        if (!missingIds.isEmpty()) {
            throw new CustomException(
                    ERROR_KEY_MISSING_ATTENDEES,
                    ERROR_MSG_ATTENDEES_NOT_FOUND + String.join(", ", missingIds));
        }
    }


    public void validateAttendeeCreateRequestParameters(AttendeeCreateRequest attendeeCreateRequest) {
        List<IndividualEntry> attendeeList = attendeeCreateRequest.getAttendees();
        Map<String, String> errorMap = new HashMap<>();

        if (attendeeList == null || attendeeList.isEmpty()) {
            log.error("ATTENDEE Object is empty in attendee request");
            throw new CustomException("ATTENDEE", "ATTENDEE Object is empty in attendee request");
        }

        String tenantId = attendeeList.get(0).getTenantId();
        for (IndividualEntry attendee : attendeeList) {

            //validate request parameters for each attendee object
            if (StringUtils.isBlank(attendee.getRegisterId())) {
                log.error("register id is empty in attendee request");
                errorMap.put("REGISTER_ID", "Register id is mandatory");
            }

            if (StringUtils.isBlank(attendee.getIndividualId())) {
                log.error("individual id is empty in attendee request");
                errorMap.put("INDIVIDUAL_ID", "Individual id is mandatory");
            }

            if (StringUtils.isBlank(attendee.getTenantId())) {
                log.error("tenant id is empty in attendee request");
                errorMap.put("TENANT_ID", "Tenant id is mandatory");
            }
        }

        if (!errorMap.isEmpty()) {
            log.error("Attendee request validation failed");
            throw new CustomException(errorMap);
        }
        validateTenantIds(attendeeCreateRequest, tenantId);
        validateDuplicateAttendeeObjects(attendeeCreateRequest);

        //validate tenantId with MDMS
        log.info("validating tenant id from MDMS and Request info");
        validateMDMSAndRequestInfoForCreateAttendee(attendeeCreateRequest);

        //validate individualId with Individual Service
        log.info("validating tenant id from MDMS and Request info");
        validateIndividualId(attendeeCreateRequest);
    }

    private void validateIndividualId(AttendeeCreateRequest attendeeCreateRequest) {
        RequestInfo requestInfo=attendeeCreateRequest.getRequestInfo();
        String tenantId=attendeeCreateRequest.getAttendees().get(0).getTenantId();
        List<String> individualIds=attendeeCreateRequest.getAttendees().stream().map(attendee->attendee.getIndividualId()).collect(Collectors.toList());
        List<String> ids= individualServiceUtil.fetchIndividualIds(individualIds,requestInfo,tenantId);

        for(String individualId:individualIds){
            if(!ids.contains(individualId)){
                throw new CustomException("INDIVIDUAL_ID_NOT_FOUND","Individual with id: "+individualId+" not found");
            }
        }
    }

    public void validateTenantIds(AttendeeCreateRequest attendeeCreateRequest, String tenantId) {
        List<IndividualEntry> attendeeList = attendeeCreateRequest.getAttendees();
        //validate if all attendee in the list have the same tenant id
        for (IndividualEntry attendee : attendeeList) {
            if (!attendee.getTenantId().equals(tenantId)) {
                log.error("All attendees dont have the same tenant id in attendee request");
                throw new CustomException("TENANT_ID", "All Attendees to be enrolled or de enrolled must have the same tenant id. Please raise new request for different tenant id");
            }
        }

    }

    /**
     * Validates the tenant IDs in the AttendeeUpdateTagRequest object.
     *
     * @param attendeeUpdateTagRequest The request object containing the list of attendees to be updated.
     * @param tenantId                 The tenant ID to validate against.
     */
    public void validateTenantIds(AttendeeUpdateTagRequest attendeeUpdateTagRequest, String tenantId) {
        List<IndividualEntry> attendeeList = attendeeUpdateTagRequest.getAttendees();

        for (IndividualEntry attendee : attendeeList) {
            if (!attendee.getTenantId().equals(tenantId)) {
                log.error(ERROR_MSG_INCONSISTENT_TENANT_ID);
                throw new CustomException(ERROR_KEY_TENANT_ID, ERROR_MSG_INCONSISTENT_TENANT_ID);
            }
        }
    }


    public void validateDuplicateAttendeeObjects(AttendeeCreateRequest attendeeCreateRequest) {
        List<IndividualEntry> attendeeList = attendeeCreateRequest.getAttendees();

        Set<String> uniqueIds = new HashSet<>();
        for (IndividualEntry attendee : attendeeList) {
            String uniqueId = attendee.getRegisterId() + attendee.getIndividualId();
            if (uniqueIds.isEmpty()) {
                uniqueIds.add(attendee.getRegisterId() + attendee.getIndividualId());
            } else if (uniqueIds.contains(uniqueId)) {
                log.error("Duplicate Attendee Objects found in request");
                throw new CustomException("ATTENDEE", "Duplicate attendee Objects present in request");
            }
            uniqueIds.add(attendee.getRegisterId() + attendee.getIndividualId());
        }
    }


    public void validateAttendeeDeleteRequestParameters(AttendeeDeleteRequest attendeeDeleteRequest, Map<String, String> errorMap) {

        List<IndividualEntry> attendeeList = attendeeDeleteRequest.getAttendees();

        if (attendeeList == null || attendeeList.isEmpty()) {
            log.error("ATTENDEE Object is empty in attendee request");
            throw new CustomException("ATTENDEES", "ATTENDEE object is mandatory");
        }

        String tenantId = attendeeList.get(0).getTenantId();
        for (IndividualEntry attendee : attendeeList) {

            //validate request parameters for each attendee object
            if (StringUtils.isBlank(attendee.getRegisterId())) {
                log.error("REGISTER_ID is empty in attendee request");
                errorMap.put("REGISTER_ID", "Register id is mandatory");
            }

            if (StringUtils.isBlank(attendee.getIndividualId())) {
                log.error("INDIVIDUAL_ID is empty in attendee request");
                errorMap.put("INDIVIDUAL_ID", "Individual id is mandatory");
            }

            if (StringUtils.isBlank(attendee.getTenantId())) {
                log.error("TENANT_ID is empty in attendee request");
                errorMap.put("TENANT_ID", "Tenant id is mandatory");
            }
        }

        if (!errorMap.isEmpty()) {
            log.error("Attendee request validation failed");
            throw new CustomException(errorMap);
        }

        validateTenantIds(attendeeDeleteRequest, tenantId);
        validateDuplicateAttendeeObjects(attendeeDeleteRequest);

        //validate tenantId with MDMS
        log.info("validating tenant id from MDMS and Request info");
        validateMDMSAndRequestInfoForDeleteAttendee(attendeeDeleteRequest);

        //validate individualId with Individual Service
        log.info("validating tenant id from MDMS and Request info");
        validateIndividualId(attendeeDeleteRequest);
    }

    private void validateIndividualId(AttendeeDeleteRequest attendeeDeleteRequest) {
        RequestInfo requestInfo=attendeeDeleteRequest.getRequestInfo();
        String tenantId=attendeeDeleteRequest.getAttendees().get(0).getTenantId();
        List<String> individualIds=attendeeDeleteRequest.getAttendees().stream().map(attendee->attendee.getIndividualId()).collect(Collectors.toList());
        List<String> ids= individualServiceUtil.fetchIndividualIds(individualIds,requestInfo,tenantId);

        for(String individualId:individualIds){
            if(!ids.contains(individualId)){
                throw new CustomException("INDIVIDUAL_ID_NOT_FOUND","Individual with id: "+individualId+" not found");
            }
        }
    }

    public void validateTenantIds(AttendeeDeleteRequest attendeeDeleteRequest, String tenantId) {
        List<IndividualEntry> attendeeList = attendeeDeleteRequest.getAttendees();
        //validate if all attendee in the list have the same tenant id
        for (IndividualEntry attendee : attendeeList) {
            if (!attendee.getTenantId().equals(tenantId)) {
                log.error("All attendees dont have the same tenant id in attendee request");
                throw new CustomException("TENANT_ID", "All Attendees to be enrolled or de enrolled must have the same tenant id. Please raise new request for different tenant id");
            }
        }

    }

    public void validateDuplicateAttendeeObjects(AttendeeDeleteRequest attendeeDeleteRequest) {
        List<IndividualEntry> attendeeList = attendeeDeleteRequest.getAttendees();

        Set<String> uniqueIds = new HashSet<>();
        for (IndividualEntry attendee : attendeeList) {
            String uniqueId = attendee.getRegisterId() + attendee.getIndividualId();
            if (uniqueIds.isEmpty()) {
                uniqueIds.add(attendee.getRegisterId() + attendee.getIndividualId());
            } else if (uniqueIds.contains(uniqueId)) {
                log.error("Duplicate Attendee Objects found in request");
                throw new CustomException("ATTENDEE", "Duplicate attendee Objects present in request");
            }
            uniqueIds.add(attendee.getRegisterId() + attendee.getIndividualId());
        }
    }

    public void validateMDMSAndRequestInfoForCreateAttendee(AttendeeCreateRequest attendeeCreateRequest) {

        RequestInfo requestInfo = attendeeCreateRequest.getRequestInfo();
        List<IndividualEntry> attendeeListFromRequest = attendeeCreateRequest.getAttendees();
        Map<String, String> errorMap = new HashMap<>();

        String tenantId = attendeeListFromRequest.get(0).getTenantId();

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, tenantId);

        //check tenant Id
        log.info("validate tenantId with MDMS");
        validateMDMSData(tenantId, mdmsData, errorMap);


        //validate request-info
        log.info("validate request info coming from api request");
        validateRequestInfo(requestInfo, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void validateMDMSAndRequestInfoForDeleteAttendee(AttendeeDeleteRequest attendeeDeleteRequest) {

        RequestInfo requestInfo = attendeeDeleteRequest.getRequestInfo();
        List<IndividualEntry> attendeeListFromRequest = attendeeDeleteRequest.getAttendees();
        Map<String, String> errorMap = new HashMap<>();

        String tenantId = attendeeListFromRequest.get(0).getTenantId();

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, tenantId);

        //check tenant Id
        log.info("validate tenantId with MDMS");
        validateMDMSData(tenantId, mdmsData, errorMap);


        //validate request-info
        log.info("validate request info coming from api request");
        validateRequestInfo(requestInfo, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }


    /**
     * Validates the MDMS data and request info for updating attendee tags.
     *
     * @param attendeeUpdateTagRequest The request object containing the list of attendees to be updated.
     */
    public void validateMDMSAndRequestInfoForUpdateTagAttendee(AttendeeUpdateTagRequest attendeeUpdateTagRequest) {

        RequestInfo requestInfo = attendeeUpdateTagRequest.getRequestInfo();
        List<IndividualEntry> attendeeListFromRequest = attendeeUpdateTagRequest.getAttendees();
        Map<String, String> errorMap = new HashMap<>();
        String tenantId = attendeeListFromRequest.get(0).getTenantId();
        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, tenantId);
        //check tenant Id
        log.info("validate tenantId with MDMS");
        validateMDMSData(tenantId, mdmsData, errorMap);
        //validate request-info
        log.info("validate request info coming from api request");
        validateRequestInfo(requestInfo, errorMap);
        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void validateAttendeeOnCreate(AttendeeCreateRequest attendeeCreateRequest
            , List<IndividualEntry> attendeeListFromDB, List<AttendanceRegister> attendanceRegisterListFromDB) {

        List<IndividualEntry> attendeeListFromRequest = attendeeCreateRequest.getAttendees();

        // Fetch individual details to get names
        List<String> individualIds = attendeeListFromRequest.stream()
                .map(IndividualEntry::getIndividualId)
                .collect(Collectors.toList());
        Map<String, String> individualIdToNameMap = new HashMap<>();
        try {
            individualServiceUtil.getIndividualDetails(individualIds, attendeeCreateRequest.getRequestInfo(),
                    attendeeListFromRequest.get(0).getTenantId())
                    .forEach(individual -> {
                        String name = individual.getName() != null && individual.getName().getGivenName() != null
                                ? individual.getName().getGivenName()
                                : individual.getId();
                        individualIdToNameMap.put(individual.getId(), name);
                    });
        } catch (Exception e) {
            log.warn("Failed to fetch individual names, will use IDs in error messages: {}", e.getMessage());
        }

        // Create register ID to name map
        Map<String, String> registerIdToNameMap = attendanceRegisterListFromDB.stream()
                .collect(Collectors.toMap(
                        AttendanceRegister::getId,
                        register -> register.getName() != null ? register.getName() : register.getId()
                ));


        // attendee cannot be added to register if register's end date has passed
        log.info("verifying that attendee cannot be added to register if register's end date has passed");
        BigDecimal currentDate = new BigDecimal(System.currentTimeMillis());
        for (AttendanceRegister attendanceRegister : attendanceRegisterListFromDB) {
            int dateComparisonResult = attendanceRegister.getEndDate().compareTo(currentDate);
            if (dateComparisonResult < 0) {
                String registerName = registerIdToNameMap.getOrDefault(attendanceRegister.getId(), attendanceRegister.getId());
                throw new CustomException("END_DATE", "Attendee cannot be enrolled as END_DATE of register '" + registerName + "' has already passed.");
            }
        }

        //attendee enrollment date, if present in request should be after start date and before end date of register
        log.info("checking attendee enrollment date should be after start date and before end date of register");
        for (AttendanceRegister attendanceRegister : attendanceRegisterListFromDB) {
            for (IndividualEntry attendeeFromRequest : attendeeListFromRequest) {
                if (attendanceRegister.getId().equals(attendeeFromRequest.getRegisterId())) {
                    if (attendeeFromRequest.getEnrollmentDate() != null) {
                        int startDateCompare = attendeeFromRequest.getEnrollmentDate().compareTo(attendanceRegister.getStartDate());
                        int endDateCompare = attendanceRegister.getEndDate().compareTo(attendeeFromRequest.getEnrollmentDate());
                        if (startDateCompare < 0 || endDateCompare < 0) {
                            String individualName = individualIdToNameMap.getOrDefault(attendeeFromRequest.getIndividualId(), attendeeFromRequest.getIndividualId());
                            String registerName = registerIdToNameMap.getOrDefault(attendeeFromRequest.getRegisterId(), attendeeFromRequest.getRegisterId());
                            throw new CustomException("ENROLLMENT_DATE"
                                    , "Enrollment date for attendee '" + individualName + "' must be within start and end date of the register '" + registerName + "'");
                        }
                    }
                }
            }
        }

        //check if attendee is already enrolled to the register
        log.info("checking if attendee is already enrolled to the register");
        for (IndividualEntry attendeeFromRequest : attendeeListFromRequest) {
            for (IndividualEntry attendeeFromDB : attendeeListFromDB) {
                if (attendeeFromRequest.getRegisterId().equals(attendeeFromDB.getRegisterId())
                        && attendeeFromRequest.getIndividualId().equals(attendeeFromDB.getIndividualId())) {//attendee present in db
                    String individualName = individualIdToNameMap.getOrDefault(attendeeFromRequest.getIndividualId(), attendeeFromRequest.getIndividualId());
                    String registerName = registerIdToNameMap.getOrDefault(attendeeFromRequest.getRegisterId(), attendeeFromRequest.getRegisterId());

                    if (attendeeFromDB.getDenrollmentDate() == null) { // already enrolled to the register
                        throw new CustomException("INDIVIDUAL_ID", "Attendee '" + individualName + "' is already enrolled in the register '" + registerName + "'");

                    }  else {
                         // Prevent re-enrollment of previously de-enrolled attendees
                         throw new CustomException("INDIVIDUAL_ID", "Attendee '" + individualName + "' was previously enrolled and de-enrolled from register '" + registerName + "'. Re-enrollment is not allowed.");
                    }
                }
            }
        }
    }

    public void validateAttendeeOnDelete(AttendeeDeleteRequest attendeeDeleteRequest,
                                         List<IndividualEntry> attendeeListFromDB, List<AttendanceRegister> attendanceRegisterListFromDB) {

        List<IndividualEntry> attendeeListFromRequest = attendeeDeleteRequest.getAttendees();


        //attendee de-enrollment date, if present in request should be before end date and after start date of register
        log.info("verifying attendee de-enrollment date, if present in request should be before end date and after start date of register");
        for (AttendanceRegister attendanceRegister : attendanceRegisterListFromDB) {
            for (IndividualEntry attendeeFromRequest : attendeeListFromRequest) {
                if (attendeeFromRequest.getDenrollmentDate() != null) {
                    int startDateCompare = attendeeFromRequest.getDenrollmentDate().compareTo(attendanceRegister.getStartDate());
                    int endDateCompare = attendanceRegister.getEndDate().compareTo(attendeeFromRequest.getDenrollmentDate());
                    if (startDateCompare < 0 || endDateCompare < 0) {
                        throw new CustomException("DE ENROLLMENT_DATE"
                                , "De enrollment date for attendee : " + attendeeFromRequest.getIndividualId() + " must be between start date and end date of the register");
                    }
                }
            }
        }

        //check if attendee is already de-enrolled from the register
        log.info("checking if attendee is already de-enrolled from the register");
        boolean attendeeDeEnrolled = true;
        for (IndividualEntry attendeeFromRequest : attendeeListFromRequest) {
            for (IndividualEntry attendeeFromDB : attendeeListFromDB) {
                if (attendeeFromRequest.getRegisterId().equals(attendeeFromDB.getRegisterId()) && attendeeFromDB.getIndividualId().equals(attendeeFromRequest.getIndividualId())) { //attendee present in db
                    if (attendeeFromDB.getDenrollmentDate() == null) {
                        attendeeDeEnrolled = false;
                        break;
                    }
                }
            }
            if (attendeeDeEnrolled) {
                throw new CustomException("INDIVIDUAL_ID", "Attendee " + attendeeFromRequest.getIndividualId() + " is already de enrolled from the register " + attendeeFromRequest.getRegisterId());
            }
        }

    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        if (requestInfo == null) {
            log.error("Request info is null");
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            log.error("User info is null");
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            log.error("User's UUID field is empty");
            throw new CustomException("USERINFO_UUID", "User's UUID is mandatory");
        }
    }

    private void validateMDMSData(String tenantId, Object mdmsData, Map<String, String> errorMap) {
        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";

        List<Object> tenantRes = null;
        try {
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (CollectionUtils.isEmpty(tenantRes))
            errorMap.put("INVALID_TENANT", "The tenant: " + tenantId + " is not present in MDMS");
    }

    /**
     * Function that validates whether attendees are project staff and whether attendees have the correct reporting staff
     *
     * @param attendeeCreateRequest
     * @return
     */

    public void validateAttendeeDetails(AttendeeCreateRequest attendeeCreateRequest) {
        String tenantId = attendeeCreateRequest.getAttendees().get(0).getTenantId();
        RequestInfo requestInfo = attendeeCreateRequest.getRequestInfo();
        List<IndividualEntry> validIndividualEntries = new ArrayList<>();

        //extracting all registerIDs coming in the request
        List<String> registerIds = attendeeCreateRequest.getAttendees().stream()
                .map(IndividualEntry::getRegisterId)
                .collect(Collectors.toList());

        //creating a register Id to First Staff Map
        Map<String, StaffPermission> registerIdToFirstStaffMap;
        if(config.isRegisterFirstOwnerStaffEnabled()) {
            log.info("Using first owner staff mapping strategy");
            registerIdToFirstStaffMap = staffService.fetchRegisterIdtoFirstOwnerStaffMap(tenantId,registerIds);
        }
        else {
            log.info("Using first staff mapping strategy");
            registerIdToFirstStaffMap = staffService.fetchRegisterIdtoFirstStaffMap(tenantId,registerIds);
        }

        if(registerIdToFirstStaffMap.isEmpty()) {
            log.error("No staff mapping found for registers: {}", registerIds);
            throw new CustomException("STAFF_MAPPING_NOT_FOUND", "No staff mapping found for the registers");
        }

        //Fetching all the attendees's uuids for hrms search
        List<String> userUuids = attendeeCreateRequest.getAttendees().stream()
                .map(IndividualEntry::getIndividualId)
                .collect(Collectors.toList());

        //getting the employee List from HRMS Search based on the attendee's uuids
        List<Employee> employeeList = hrmsUtil.getEmployee(tenantId, userUuids, requestInfo);

        Map<String, Employee> individualIdVsEmployeeMap = employeeList.stream()
                .collect(Collectors.toMap(Employee::getUuid, emp -> emp));

        //looping through attendees for validating their details
        for (IndividualEntry entry : attendeeCreateRequest.getAttendees()) {
            try {

                if (!individualIdVsEmployeeMap.containsKey(entry.getIndividualId())) {
                    throw new CustomException("HRMS_EMPLOYEE_NOT_FOUND", "Employee not present in HRMS for the individual ID - " + entry.getIndividualId());
                }

                //fetch reportingTo uuids from employees assignments
                List<String> reportingToList = individualIdVsEmployeeMap.get(entry.getIndividualId()).getAssignments().stream()
                        .map(Assignment::getReportingTo)
                        .filter(reportingTo -> reportingTo != null && !reportingTo.isEmpty())
                        .collect(Collectors.toList());

                //fetch the first staff's User Id
                String reportersUuid = registerIdToFirstStaffMap.get(entry.getRegisterId()).getUserId();

                List<Employee> reportersEmployeeList = hrmsUtil.getEmployee(tenantId, Collections.singletonList(reportersUuid), requestInfo);
                if(reportersEmployeeList.isEmpty())
                    throw new CustomException("FAILED_TO_FETCH_REPORTERS_UUID", "Failed to fetch reporters hrms uuid for userserviceId - " + reportersUuid);

                if (!reportingToList.contains(reportersEmployeeList.get(0).getUser().getUserServiceUuid())) {
                    //throw validation error if attendee's reportingTo is not First Staff of the Register
                    throw new CustomException("REPORTING_STAFF_INCORRECT_FOR_ATTENDEE", "Attendees reporting uuid does not match with the register owner uuid");
                }
                validIndividualEntries.add(entry);
            }
            catch (CustomException e) {
                // Re-throw CustomException as-is to preserve the original error code and message
                log.error("Validation failed for attendee {}: {} - {}", entry.getIndividualId(), e.getCode(), e.getMessage());
                throw e;
            }
            catch (Exception e) {
                // Wrap unexpected exceptions
                log.error("Unexpected error while validating attendee {}: {}", entry.getIndividualId(), e.toString(), e);
                throw new CustomException("ATTENDEE_VALIDATION_FAILED",
                        "Failed to validate attendee with individual ID - " + entry.getIndividualId() + ": " + e.getMessage());
            }
        }
        attendeeCreateRequest.setAttendees(validIndividualEntries);
    }
}



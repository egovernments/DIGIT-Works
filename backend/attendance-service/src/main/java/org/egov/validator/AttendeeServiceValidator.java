package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.util.MDMSUtils;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendeeCreateRequest;
import org.egov.web.models.AttendeeDeleteRequest;
import org.egov.web.models.IndividualEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.util.AttendanceServiceConstants.MASTER_TENANTS;
import static org.egov.util.AttendanceServiceConstants.MDMS_TENANT_MODULE_NAME;

@Component
@Slf4j
public class AttendeeServiceValidator {

    @Autowired
    private MDMSUtils mdmsUtils;

    public void validateAttendeeCreateRequestParameters(AttendeeCreateRequest attendeeCreateRequest) {
        List<IndividualEntry> attendeeList = attendeeCreateRequest.getAttendees();

        if (attendeeList == null || attendeeList.size() == 0) {
            log.error("ATTENDEE Object is empty in attendee request");
            throw new CustomException("ATTENDEES", "ATTENDEE object is mandatory");
        }

        String tenantId = attendeeList.get(0).getTenantId();
        for (IndividualEntry attendee : attendeeList) {

            //validate request parameters for each attendee object

            if (ObjectUtils.isEmpty(attendee)) {
                log.error("ATTENDEE Object is empty in attendee request");
                throw new CustomException("ATTENDEE", "ATTENDEE is mandatory");
            }
            if (StringUtils.isBlank(attendee.getRegisterId())) {
                log.error("register id is empty in attendee request");
                throw new CustomException("REGISTER_ID", "Register id is mandatory");
            }

            if (StringUtils.isBlank(attendee.getIndividualId())) {
                log.error("individual id is empty in attendee request");
                throw new CustomException("INDIVIDUAL_ID", "Individual id is mandatory");
            }

            if (StringUtils.isBlank(attendee.getTenantId())) {
                log.error("tenant id is empty in attendee request");
                throw new CustomException("TENANT_ID", "Tenant id is mandatory");
            }

            //validate if all attendee in the list have the same tenant id
            if (!attendee.getTenantId().equals(tenantId)) {
                log.error("All attendees dont have the same tenant id in attendee request");
                throw new CustomException("TENANT_ID", "All Attendees to be enrolled or de enrolled must have the same tenant id. Please raise new request for different tenant id");
            }

        }

        //check for duplicate attendee objects (with same registerId and individualId)
        //1. create unique identity list
        //2. check for duplicate entries
        List<String> uniqueIds = new ArrayList<>();
        for (IndividualEntry attendee : attendeeList) {
            uniqueIds.add(attendee.getRegisterId() + " " + attendee.getIndividualId());
        }
        for (String id : uniqueIds) {
            long count = uniqueIds.stream().filter(uniqueId -> id.equals(uniqueId)).count();
            if (count > 1) {
                log.error("Duplicate Attendee Objects present in request");
                throw new CustomException("ATTENDEE", "Duplicate Attendee Objects present in request");
            }
        }
    }


    public void validateAttendeeDeleteRequestParameters(AttendeeDeleteRequest attendeeDeleteRequest) {

        List<IndividualEntry> attendeeList = attendeeDeleteRequest.getAttendees();

        if (attendeeList == null || attendeeList.size() == 0) {
            log.error("ATTENDEE Object is empty in attendee request");
            throw new CustomException("ATTENDEES", "ATTENDEE object is mandatory");
        }

        String tenantId = attendeeList.get(0).getTenantId();
        for (IndividualEntry attendee : attendeeList) {

            //validate request parameters for each attendee object

            if (ObjectUtils.isEmpty(attendee)) {
                log.error("ATTENDEE Object is empty in attendee request");
                throw new CustomException("ATTENDEE", "ATTENDEE is mandatory");
            }
            if (StringUtils.isBlank(attendee.getRegisterId())) {
                log.error("REGISTER_ID is empty in attendee request");
                throw new CustomException("REGISTER_ID", "Register id is mandatory");
            }

            if (StringUtils.isBlank(attendee.getIndividualId())) {
                log.error("INDIVIDUAL_ID is empty in attendee request");
                throw new CustomException("INDIVIDUAL_ID", "Individual id is mandatory");
            }

            if (StringUtils.isBlank(attendee.getTenantId())) {
                log.error("TENANT_ID is empty in attendee request");
                throw new CustomException("TENANT_ID", "Tenant id is mandatory");
            }

            //validate if all attendee in the list have the same tenant id
            if (!attendee.getTenantId().equals(tenantId)) {
                log.error("All attendees dont have the same tenant id in attendee request");
                throw new CustomException("TENANT_ID", "All Attendees to be enrolled or de enrolled must have the same tenant id. Please raise new request for different tenant id");
            }

        }

        //check for duplicate attendee objects (with same registerId and individualId)
        //1. create unique identity list
        //2. check for duplicate entries
        List<String> uniqueIds = new ArrayList<>();
        for (IndividualEntry attendee : attendeeList) {
            uniqueIds.add(attendee.getRegisterId() + " " + attendee.getIndividualId());
        }
        for (String id : uniqueIds) {
            long count = uniqueIds.stream().filter(uniqueId -> id.equals(uniqueId)).count();
            if (count > 1) {
                log.error("Duplicate Attendee Objects present in request");
                throw new CustomException("ATTENDEE", "Duplicate Attendee Objects present in request");
            }
        }
    }

    public void validateMDMSAndRequestInfoForCreateAttendee(AttendeeCreateRequest attendeeCreateRequest) {

        RequestInfo requestInfo = attendeeCreateRequest.getRequestInfo();
        List<IndividualEntry> attendeeListFromRequest = attendeeCreateRequest.getAttendees();
        Map<String, String> errorMap = new HashMap<>();

        String tenantId = attendeeListFromRequest.get(0).getTenantId();
        //split the tenantId
        String rootTenantId = tenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, rootTenantId);

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
        //split the tenantId
        String rootTenantId = tenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, rootTenantId);

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

        //validate tenantId with MDMS
        log.info("validating tenant id from MDMS and Request info");
        validateMDMSAndRequestInfoForCreateAttendee(attendeeCreateRequest);

        List<IndividualEntry> attendeeListFromRequest = attendeeCreateRequest.getAttendees();


        // attendee cannot be added to register if register's end date has passed
        log.info("verifying that attendee cannot be added to register if register's end date has passed");
        BigDecimal currentDate = new BigDecimal(System.currentTimeMillis());
        for (AttendanceRegister attendanceRegister : attendanceRegisterListFromDB) {
            int dateComparisonResult = attendanceRegister.getEndDate().compareTo(currentDate);
            if (dateComparisonResult < 0) {
                throw new CustomException("END_DATE", "Attendee cannot be enrolled as END_DATE of register id " + attendanceRegister.getId() + " has already passed.");
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
                            throw new CustomException("ENROLLMENT_DATE"
                                    , "Enrollment date for attendee : " + attendeeFromRequest.getIndividualId() + " must be within start and end date of the register");
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
                    if (attendeeFromDB.getDenrollmentDate() == null) { // already enrolled to the register
                        throw new CustomException("INDIVIDUAL_ID", "Attendee " + attendeeFromRequest.getIndividualId() + " is already enrolled in the register " + attendeeFromRequest.getRegisterId());

                    }
                }
            }
        }
    }

    public void validateAttendeeOnDelete(AttendeeDeleteRequest attendeeDeleteRequest,
                                       List<IndividualEntry> attendeeListFromDB, List<AttendanceRegister> attendanceRegisterListFromDB) {
        //validate tenantId with MDMS
        log.info("validating tenant id from MDMS and Request info");
        validateMDMSAndRequestInfoForDeleteAttendee(attendeeDeleteRequest);

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
}



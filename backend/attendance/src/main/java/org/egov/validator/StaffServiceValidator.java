package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.service.AttendanceRegisterService;
import org.egov.tracer.model.CustomException;
import org.egov.util.MDMSUtils;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import org.egov.util.HRMSUtil;

import java.math.BigDecimal;
import java.util.*;

import static org.egov.util.AttendanceServiceConstants.*;



@Component
@Slf4j
public class StaffServiceValidator {

    private final MDMSUtils mdmsUtils;


    @Autowired
    public StaffServiceValidator(MDMSUtils mdmsUtils) {
        this.mdmsUtils = mdmsUtils;
    }


    public void validateMDMSAndRequestInfoForStaff(StaffPermissionRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<StaffPermission> staffPermissionListFromRequest = request.getStaff();
        Map<String, String> errorMap = new HashMap<>();

        String tenantId = staffPermissionListFromRequest.get(0).getTenantId();

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, tenantId);

        //validate request-info
        log.info("validate request info coming from api request");
        validateRequestInfo(requestInfo, errorMap);

        //check tenant Id
        log.info("validate tenantId with MDMS");
        validateMDMSData(tenantId, mdmsData, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        if (requestInfo == null) {
            log.error("Request info is null");
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            log.error("UserInfo is null");
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            log.error("User's UUID field is empty");
            throw new CustomException("USERINFO_UUID", "User's UUID is mandatory");
        }
    }


    public void validateStaffPermissionRequestParameters(StaffPermissionRequest staffPermissionRequest) {
        List<StaffPermission> staffPermissionList = staffPermissionRequest.getStaff();
        Map<String, String> errorMap = new HashMap<>();

        if (staffPermissionList == null || staffPermissionList.isEmpty()) {
            log.error("Staff Object is empty in staff request");
            throw new CustomException("STAFF", "Staff is mandatory");
        }

        String baseTenantId = staffPermissionList.get(0).getTenantId();
        for (StaffPermission staffPermission : staffPermissionList) {

            //validate request parameters for each staff object
            if (StringUtils.isBlank(staffPermission.getRegisterId())) {
                log.error("Register id is empty in staff request");
                errorMap.put("REGISTER_ID", "Register id is mandatory");
            }

            if (StringUtils.isBlank(staffPermission.getUserId())) {
                log.error("User id is empty in staff request");
                errorMap.put("USER_ID", "User id is mandatory");
            }

            if (StringUtils.isBlank(staffPermission.getTenantId())) {
                log.error("Tenant id is empty in staff request");
                errorMap.put("TENANT_ID", "Tenant id is mandatory");
            }
        }

        if (!errorMap.isEmpty()) {
            log.error("Attendee request validation failed");
            throw new CustomException(errorMap);
        }

        validateTenantIds(staffPermissionRequest, baseTenantId);
        validateDuplicateStaffObjects(staffPermissionRequest);


        //validate tenant id with mdms and request info
        log.info("validating tenant id from MDMS and Request info");
        validateMDMSAndRequestInfoForStaff(staffPermissionRequest);
    }

    public void validateTenantIds(StaffPermissionRequest staffPermissionRequest, String tenantId) {
        List<StaffPermission> staffPermissionList = staffPermissionRequest.getStaff();
        //validate if all staff in the list have the same tenant id
        for (StaffPermission staffPermission : staffPermissionList) {
            if (!staffPermission.getTenantId().equals(tenantId)) {
                log.error("All staff objects do not have the same tenant id");
                throw new CustomException("TENANT_ID", "All Staff to be enrolled or de enrolled must have the same tenant id. Please raise new request for different tenant id");
            }
        }

    }

    public void validateDuplicateStaffObjects(StaffPermissionRequest staffPermissionRequest) {
        List<StaffPermission> staffPermissionList = staffPermissionRequest.getStaff();

        Set<String> uniqueIds = new HashSet<>();
        for (StaffPermission staffPermission : staffPermissionList) {
            String uniqueId = staffPermission.getRegisterId() + staffPermission.getUserId();
            if (uniqueIds.isEmpty()) {
                uniqueIds.add(staffPermission.getRegisterId() + staffPermission.getUserId());
            } else if (uniqueIds.contains(uniqueId)) {
                log.error("Duplicate Staff Objects found in request");
                throw new CustomException("STAFF", "Duplicate Staff Objects present in request");
            }
            uniqueIds.add(staffPermission.getRegisterId() + staffPermission.getUserId());
        }
    }

    public void validateStaffUpdateRequestParameters(StaffPermissionRequest staffPermissionRequest) {
        Set<String> userRoles = HRMSUtil.getUserRoleCodes(staffPermissionRequest.getRequestInfo());
        if (!userRoles.contains(ROLE_CAMPAIGN_MANAGER)) {
            throw new CustomException(ERROR_KEY_UNAUTHORIZED, ERROR_MSG_UNAUTHORIZED_UPDATE_STAFF);
        }

        List<StaffPermission> staffPermissionList = staffPermissionRequest.getStaff();
        Map<String, String> errorMap = new HashMap<>();

        if (staffPermissionList == null || staffPermissionList.isEmpty()) {
            throw new CustomException("STAFF", "Staff is mandatory");
        }

        String baseTenantId = staffPermissionList.get(0).getTenantId();
        for (StaffPermission staffPermission : staffPermissionList) {
            if (StringUtils.isBlank(staffPermission.getId())) {
                errorMap.put("ID", "id is mandatory for update");
            }
            if (StringUtils.isBlank(staffPermission.getRegisterId())) {
                errorMap.put("REGISTER_ID", "Register id is mandatory");
            }
            if (StringUtils.isBlank(staffPermission.getUserId())) {
                errorMap.put("USER_ID", "User id is mandatory");
            }
            if (StringUtils.isBlank(staffPermission.getTenantId())) {
                errorMap.put("TENANT_ID", "Tenant id is mandatory");
            }
        }

        if (!errorMap.isEmpty()) {
            throw new CustomException(errorMap);
        }

        validateTenantIds(staffPermissionRequest, baseTenantId);

        // no duplicate (registerId + userId)
        Set<String> uniqueKeys = new HashSet<>();
        for (StaffPermission staffPermission : staffPermissionList) {
            String key = staffPermission.getRegisterId() + staffPermission.getUserId();
            if (!uniqueKeys.add(key)) {
                throw new CustomException("STAFF", "Duplicate Staff Objects present in update request");
            }
        }

        validateMDMSAndRequestInfoForStaff(staffPermissionRequest);
    }

    public void validateStaffOnUpdate(StaffPermissionRequest staffPermissionRequest,
                                      List<StaffPermission> staffFromDB,
                                      List<AttendanceRegister> registers) {
        List<StaffPermission> staffFromRequest = staffPermissionRequest.getStaff();

        // Build lookup maps
        Map<String, StaffPermission> dbById = staffFromDB.stream()
                .collect(java.util.stream.Collectors.toMap(StaffPermission::getId, s -> s));
        Map<String, AttendanceRegister> registerById = registers.stream()
                .collect(java.util.stream.Collectors.toMap(AttendanceRegister::getId, r -> r));

        for (StaffPermission staff : staffFromRequest) {
            StaffPermission dbRecord = dbById.get(staff.getId());
            if (dbRecord == null) {
                throw new CustomException(ERROR_KEY_INVALID_ID, ERROR_MSG_STAFF_NOT_FOUND_IN_DB_PREFIX + staff.getId());
            }

            AttendanceRegister register = registerById.get(staff.getRegisterId());
            if (register == null) {
                continue;
            }

            // Validate enrollmentDate if provided
            if (staff.getEnrollmentDate() != null) {
                if (staff.getEnrollmentDate().compareTo(register.getStartDate()) < 0) {
                    throw new CustomException(ERROR_KEY_ENROLLMENT_DATE,
                            "Enrollment date for staff " + staff.getUserId() + ERROR_MSG_ENROLLMENT_DATE_BEFORE_REGISTER_START);
                }
                // Only enrollmentDate updated, denrollmentDate unchanged — new enrollment must not exceed existing de-enrollment
                if (staff.getDenrollmentDate() == null && dbRecord.getDenrollmentDate() != null) {
                    if (staff.getEnrollmentDate().compareTo(dbRecord.getDenrollmentDate()) > 0) {
                        throw new CustomException(ERROR_KEY_ENROLLMENT_DATE,
                                "Enrollment date for staff " + staff.getUserId() + ERROR_MSG_ENROLLMENT_DATE_EXCEEDS_DENROLLMENT);
                    }
                }
            }

            // Resolve effective enrollmentDate: if both dates are being updated, compare new vs new only
            BigDecimal effectiveEnrollmentDate = staff.getEnrollmentDate() != null
                    ? staff.getEnrollmentDate()
                    : dbRecord.getEnrollmentDate();

            // Validate denrollmentDate if provided
            if (staff.getDenrollmentDate() != null) {
                if (staff.getDenrollmentDate().compareTo(effectiveEnrollmentDate) < 0) {
                    throw new CustomException(ERROR_KEY_DENROLLMENT_DATE,
                            "De-enrollment date for staff " + staff.getUserId() + ERROR_MSG_DENROLLMENT_DATE_BEFORE_ENROLLMENT);
                }
                if (register.getEndDate() != null &&
                        staff.getDenrollmentDate().compareTo(register.getEndDate()) > 0) {
                    throw new CustomException(ERROR_KEY_DENROLLMENT_DATE,
                            "De-enrollment date for staff " + staff.getUserId() + ERROR_MSG_DENROLLMENT_DATE_AFTER_REGISTER_END);
                }
            }
        }
    }

    public void validateStaffPermissionOnCreate(StaffPermissionRequest request, List<StaffPermission> staffPermissionListFromDB,
                                                List<AttendanceRegister> attendanceRegisterListFromDB) {

        List<StaffPermission> staffPermissionListFromRequest = request.getStaff();

        // staff cannot be added to register if register's end date has passed
        log.info("checking that staff cannot be added to register if register's end date has passed");
        BigDecimal enrollmentDate = new BigDecimal(System.currentTimeMillis());
        for (AttendanceRegister attendanceRegister : attendanceRegisterListFromDB) {
            int dateComparisonResult = attendanceRegister.getEndDate().compareTo(enrollmentDate);
            if (dateComparisonResult < 0) {
                log.error("Staff cannot be enrolled as END_DATE of register id " + attendanceRegister.getId() + " has already passed.");
                throw new CustomException("END_DATE", "Staff cannot be enrolled as END_DATE of register id " + attendanceRegister.getId() + " has already passed.");
            }
        }

        //check if staff user id exists in staff table for the given register id. If yes check the deenrollment date. If staffId does not exist new staff can still be enrolled to the register
        if (staffPermissionListFromDB != null) {
            for (StaffPermission staffFromRequest : staffPermissionListFromRequest) {//list of staff from request
                StaffPermission staff = staffPermissionListFromDB.stream()
                        .filter(s -> s.getUserId().equals(staffFromRequest.getUserId()) && s.getRegisterId().equals(staffFromRequest.getRegisterId()))
                        .findFirst().orElse(null);
                if (staff != null && staff.getDenrollmentDate() == null) {
                    throw new CustomException("USER_id", "Staff " + staff.getUserId() + " is already enrolled in the register " + staff.getRegisterId());
                }
            }
        }

    }


    public void validateStaffPermissionOnDelete(StaffPermissionRequest staffPermissionRequest
            , List<StaffPermission> staffPermissionListFromDB, List<AttendanceRegister> attendanceRegisterListFromDB) {

        RequestInfo requestInfo = staffPermissionRequest.getRequestInfo();
        List<StaffPermission> staffPermissionListFromRequest = staffPermissionRequest.getStaff();

        boolean staffExists = false;
        boolean staffDeenrolled = true;


        //check is staff user id exists in staff table. If yes check if the de enrollment date is null
        log.info("checking if the de enrollment date of staff is null");
        for (StaffPermission staffPermissionFromRequest : staffPermissionListFromRequest) {
            for (StaffPermission staffPermissionFromDB : staffPermissionListFromDB) {
                if (staffPermissionFromRequest.getRegisterId().equals(staffPermissionFromDB.getRegisterId()) &&
                        staffPermissionFromRequest.getUserId().equals(staffPermissionFromDB.getUserId())) {
                    staffExists = true;
                    if (staffPermissionFromDB.getDenrollmentDate() == null) {
                        staffDeenrolled = false;
                        break;
                    }
                }
            }
            if (!staffExists)
                throw new CustomException("USER_ID", "Staff with the given user id: " + staffPermissionFromRequest.getUserId() + " is not linked with the given register id");//handled
            if (staffDeenrolled)
                throw new CustomException("USER_ID", "Staff with the given user id : " + staffPermissionFromRequest.getUserId() + " is already de enrolled from the register");
        }


        //staff list size associated with the register (At least one staff should remain with a register before de enrollment)
        //initialize request and DB hashmaps with registerId as key
        log.info("checking that atleast one staff should remain enrolled to a register before de enrollment");
        HashMap<String, Integer> staffCountInEachRegisterIdFromRequest = new HashMap<>();
        HashMap<String, Integer> staffCountInEachRegisterIdFromDB = new HashMap<>();
        for (AttendanceRegister attendanceRegister : attendanceRegisterListFromDB) {
            staffCountInEachRegisterIdFromRequest.put(attendanceRegister.getId().toString(), 0);
            staffCountInEachRegisterIdFromDB.put(attendanceRegister.getId().toString(), 0);
        }


        //<registerId,staffCount> from staffRequest - number of de enrollments from each register
        for (StaffPermission staffPermissionFromRequest : staffPermissionListFromRequest) {
            String staffRegisterId = staffPermissionFromRequest.getRegisterId();
            if (staffCountInEachRegisterIdFromRequest.containsKey(staffRegisterId)) {
                int count = staffCountInEachRegisterIdFromRequest.get(staffRegisterId);
                count++;
                staffCountInEachRegisterIdFromRequest.put(staffRegisterId, count);
            }
        }


        //<registerId,staffCount> from StaffDB data number of staff enrolled to each register in db
        for (StaffPermission staffPermissionFromDB : staffPermissionListFromDB) {
            String staffRegisterId = staffPermissionFromDB.getRegisterId();
            if (staffCountInEachRegisterIdFromDB.containsKey(staffRegisterId) && staffPermissionFromDB.getDenrollmentDate() == null) {
                int count = staffCountInEachRegisterIdFromDB.get(staffRegisterId);
                count++;
                staffCountInEachRegisterIdFromDB.put(staffRegisterId, count);
            }
        }

        //match the regitser ids between request and db. subtract the staff, If <1 throw error
        for (String registerId : staffCountInEachRegisterIdFromRequest.keySet()) {
            if (staffCountInEachRegisterIdFromDB.containsKey(registerId)) {
                int result = staffCountInEachRegisterIdFromDB.get(registerId) - staffCountInEachRegisterIdFromRequest.get(registerId);
                if (result < 1) {
                    throw new CustomException("MIN_STAFF_REQUIRED", "Atleast one staff should be associated" +
                            "with the register. Number of staff in register id : " + registerId + " after de enrollment operation would be " + result);
                }
            }
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

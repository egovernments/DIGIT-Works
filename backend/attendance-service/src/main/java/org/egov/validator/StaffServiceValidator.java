package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.util.AttendanceServiceConstants.MASTER_TENANTS;
import static org.egov.util.AttendanceServiceConstants.MDMS_TENANT_MODULE_NAME;


@Component
@Slf4j
public class StaffServiceValidator {

    @Autowired
    private MDMSUtils mdmsUtils;

    @Autowired
    private AttendanceRegisterService attendanceRegisterService;


    public void validateCreateStaffPermission(StaffPermissionRequest request, List<StaffPermission> staffPermissionListFromDB
            , List<AttendanceRegister> attendanceRegisterListFromDB) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<StaffPermission> staffPermissionListFromRequest = request.getStaff();
        Map<String, String> errorMap = new HashMap<>();

        String tenantId = staffPermissionListFromRequest.get(0).getTenantId();
        //split the tenantId
        String rootTenantId = tenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, rootTenantId);

        //check tenant Id
        validateMDMSData(tenantId, mdmsData, errorMap);


        //validate request-info
        validateRequestInfo(requestInfo, errorMap);


        //check staff-request
        validateCreateStaffPermission(request, staffPermissionListFromDB, attendanceRegisterListFromDB, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void validateDeleteStaffPermission(StaffPermissionRequest request, List<StaffPermission> staffPermissionListFromDB
            , List<AttendanceRegister> attendanceRegisterListFromDB) {
        Map<String, String> errorMap = new HashMap<>();
        RequestInfo requestInfo = request.getRequestInfo();
        List<StaffPermission> staffPermissionListFromRequest = request.getStaff();

        String tenantId = staffPermissionListFromRequest.get(0).getTenantId();
        //split the tenantId
        String rootTenantId = tenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, rootTenantId);

        //check tenant Id
        validateMDMSData(tenantId, mdmsData, errorMap);


        //validate request-info
        validateRequestInfo(requestInfo, errorMap);

        //check staff-request
        validateDeleteStaffPermission(request, staffPermissionListFromDB, attendanceRegisterListFromDB, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }


    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            throw new CustomException("USERINFO_UUID", "User's UUID is mandatory");
        }
    }


    public void validateStaffPermissionRequestParameters(StaffPermissionRequest staffPermissionRequest) {
        List<StaffPermission> staffPermissionList = staffPermissionRequest.getStaff();

        String baseTenantId = staffPermissionList.get(0).getTenantId();
        for (StaffPermission staffPermission : staffPermissionList) {

            //validate request parameters for each staff object

            if (ObjectUtils.isEmpty(staffPermission)) {
                throw new CustomException("STAFF", "Staff is mandatory");
            }
            if (StringUtils.isBlank(staffPermission.getRegisterId())) {
                throw new CustomException("REGISTER_ID", "Register id is mandatory");
            }

            if (StringUtils.isBlank(staffPermission.getUserId())) {
                throw new CustomException("USER_ID", "User id is mandatory");
            }

            if (StringUtils.isBlank(staffPermission.getTenantId())) {
                throw new CustomException("TENANT_ID", "Tenant id is mandatory");
            }

            //validate if all staff in the list have the same tenant id
            if (!staffPermission.getTenantId().equals(baseTenantId)) {
                throw new CustomException("TENANT_ID", "All Staff to be enrolled or de enrolled must have the same tenant id. Please raise new request for different tenant id");
            }

        }

        //check for duplicate staff objects (with same registerId and userId)
        //1. create unique identity list
        //2. check for duplicate entries
        List<String> uniqueIds = new ArrayList<>();
        for (StaffPermission staffPermission : staffPermissionList) {
            uniqueIds.add(staffPermission.getRegisterId() + " " + staffPermission.getUserId());
        }
        for (String id : uniqueIds) {
            long count = uniqueIds.stream().filter(uniqueId -> id.equals(uniqueId)).count();
            if (count > 1) {
                throw new CustomException("STAFF", "Duplicate Staff Objects present in request");
            }
        }
    }

    private void validateCreateStaffPermission(StaffPermissionRequest request, List<StaffPermission> staffPermissionListFromDB, List<AttendanceRegister> attendanceRegisterListFromDB,
                                               Map<String, String> errorMap) {
        List<StaffPermission> staffPermissionListFromRequest = request.getStaff();

        // staff cannot be added to register if register's end date has passed
        BigDecimal enrollmentDate = new BigDecimal(System.currentTimeMillis());
        for (AttendanceRegister attendanceRegister : attendanceRegisterListFromDB) {
            int dateComparisonResult = attendanceRegister.getEndDate().compareTo(enrollmentDate);
            if (dateComparisonResult < 0) {
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


    private void validateDeleteStaffPermission(StaffPermissionRequest staffPermissionRequest
            , List<StaffPermission> staffPermissionListFromDB, List<AttendanceRegister> attendanceRegisterListFromDB, Map<String, String> errorMap) {
        RequestInfo requestInfo = staffPermissionRequest.getRequestInfo();
        List<StaffPermission> staffPermissionListFromRequest = staffPermissionRequest.getStaff();

        boolean staffExists = false;
        boolean staffDeenrolled = true;


        //check is staff user id exists in staff table. If yes check if the de enrollment date is not null
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
        HashMap<String, Integer> staffCountInEachRegisterIdFromRequest = new HashMap<>();
        HashMap<String, Integer> staffCountInEachRegisterIdFromDB = new HashMap<>();
        for (AttendanceRegister attendanceRegister : attendanceRegisterListFromDB) {
            staffCountInEachRegisterIdFromRequest.put(attendanceRegister.getId().toString(), 0);
            staffCountInEachRegisterIdFromDB.put(attendanceRegister.getId().toString(), 0);
        }


        //<registerId,staffCount> from staffRequest - number of de enrollments from each register
        for (StaffPermission staffPermissionFromRequest : staffPermissionListFromRequest) {
            String staffRegisterId = staffPermissionFromRequest.getRegisterId().toString();
            if (staffCountInEachRegisterIdFromRequest.containsKey(staffRegisterId)) {
                int count = staffCountInEachRegisterIdFromRequest.get(staffRegisterId);
                count++;
                staffCountInEachRegisterIdFromRequest.put(staffRegisterId, count);
            }
        }


        //<registerId,staffCount> from StaffDB data number od staff enrolled to each register in db
        for (StaffPermission staffPermissionFromDB : staffPermissionListFromDB) {
            String staffRegisterId = staffPermissionFromDB.getRegisterId().toString();
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

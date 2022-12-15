package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.AttendanceRepository;
import org.egov.service.AttendanceService;
import org.egov.tracer.model.CustomException;
import org.egov.util.MDMSUtils;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

import static org.egov.util.AttendanceServiceConstants.MASTER_TENANTS;
import static org.egov.util.AttendanceServiceConstants.MDMS_TENANT_MODULE_NAME;


@Component
@Slf4j
public class StaffServiceValidator {

    @Autowired
    private MDMSUtils mdmsUtils;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;



    public void validateCreateStaffPermission(StaffPermissionRequest request,List<StaffPermission> staffPermissionList
            ,List<AttendanceRegister> attendanceRegisterList) {
        RequestInfo requestInfo=request.getRequestInfo();
        Map<String, String> errorMap = new HashMap<>();

            String rootTenantId = staffPermissionList.get(0).getTenantId();
            //split the tenantId
            rootTenantId = rootTenantId.split("\\.")[0];

            Object mdmsData = mdmsUtils.mDMSCall(requestInfo, rootTenantId);

            //check tenant Id
            validateMDMSData(staffPermissionList.get(0), mdmsData, errorMap);


            //validate request-info
            validateRequestInfo(requestInfo, errorMap);

            //check staff-request
            validateCreateStaffPermission(staffPermissionList,attendanceRegisterList, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void validateCreateStaffPermissionRequestParameters(StaffPermissionRequest staffPermissionRequest){
        List<StaffPermission> staffPermissionList = staffPermissionRequest.getStaffPermissionList();

        //validate if all staff in the list have the same tenant id
        String baseTenantId=staffPermissionList.get(0).getTenantId();
        for(StaffPermission staffPermission:staffPermissionList) {
            if (StringUtils.isBlank(staffPermission.getTenantId())) {
                throw new CustomException("TENANT_ID", "Tenant id is mandatory");
            }
            if (!staffPermission.getTenantId().equals(baseTenantId)) {
                throw new CustomException("TENANT_ID", "All Staff to be enrolled must have the same tenant id. Please raise new request for different tenant id");
            }
        }

        //validate request parameters for each staff object
        for (StaffPermission staffPermission : staffPermissionList) {
            if (staffPermission == null) {
                throw new CustomException("STAFF", "Staff is mandatory");
            }
            if (StringUtils.isBlank(staffPermission.getRegisterId())) {
                throw new CustomException("REGISTER_ID", "Register id is mandatory");
            }

            if (StringUtils.isBlank(staffPermission.getUserId())) {
                throw new CustomException("USER_ID", "User id is mandatory");
            }

        }
    }

    public void validateDeleteStaffPermission(StaffPermissionRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        List<StaffPermission> staffPermissionList = request.getStaffPermissionList();
        RequestInfo requestInfo = request.getRequestInfo();


        //validate if all staff in the list have the same tenant id
        String baseTenantId=staffPermissionList.get(0).getTenantId();
        for(StaffPermission staffPermission:staffPermissionList) {
            if (StringUtils.isBlank(staffPermission.getTenantId())) {
                throw new CustomException("TENANT_ID", "Tenant id is mandatory");
            }
            if (!staffPermission.getTenantId().equals(baseTenantId)) {
                throw new CustomException("TENANT_ID", "All Staff to be deenrolled must have the same tenant id. Please raise new request for different tenant id");
            }
        }

        String rootTenantId = staffPermissionList.get(0).getTenantId();
        //split the tenantId
        rootTenantId = rootTenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, rootTenantId);

        //check tenant Id
        validateMDMSData(staffPermissionList.get(0), mdmsData, errorMap);


        //validate request-info
        validateRequestInfo(requestInfo, errorMap);

        //check staff-request
        validateDeleteStaffPermission(request, errorMap);



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
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }

    private void validateCreateStaffPermission(List<StaffPermission> staffPermissionList, List<AttendanceRegister> attendanceRegisterList,
                                               Map<String, String> errorMap) {

        //check if the register id exists

        // check if staff tenant id is same as register tenant id
        /*if (!getAttendanceRegisters.get(0).getTenantId().equals(staffPermissionList.get(0).getTenantId()))
            throw new CustomException("TENANT_ID", "Staff tenant id is not same as register tenant id");*/

        // staff cannot be added to register if register's end date has passed
        Date currentDT = new Date();
        BigDecimal enrollmentDate = new BigDecimal(currentDT.getTime());
        for (AttendanceRegister attendanceRegister : attendanceRegisterList) {

            int dateComparisonResult = attendanceRegister.getEndDate().compareTo(enrollmentDate.doubleValue());
            if (dateComparisonResult < 0) {
                throw new CustomException("END_DATE", "Staff cannot be enrolled as END_DATE of register id " + attendanceRegister.getId() + " has already passed.");
            }
        }


            //check is staff user id exists in staff table for the given register id. If yes check the deenrollment date
            List<StaffPermission> staffPermissionListForAttendanceRegister = attendanceRegister.getStaff();
            if (staffPermissionListForAttendanceRegister != null) {
                for (StaffPermission staffPermission : staffPermissionList) {//list of staff from request
                    for (StaffPermission staff : staffPermissionListForAttendanceRegister) {  //list of staff from searched register
                        if (staff.getUserId().equals(staffPermission.getUserId()) && attendanceRegister.getId().toString().equals(staffPermission.getRegisterId())) {
                            if (staff.getDenrollmentDate() == null) {
                                throw new CustomException("USER_id", "Staff is already enrolled in the register");
                            }
                        }
                    }
                }
            }

        }
    }


    private void validateDeleteStaffPermission(StaffPermissionRequest staffPermissionRequest, Map<String, String> errorMap) {
        RequestInfo requestInfo = staffPermissionRequest.getRequestInfo();
        List<StaffPermission> staffPermissionList = staffPermissionRequest.getStaffPermissionList();
        Set<String> registerIds = new HashSet<>();

        boolean staff_exists = false;
        boolean staff_deenrolled = true;

        for (StaffPermission staffPermission : staffPermissionList) {
            if (staffPermission == null) {
                throw new CustomException("STAFF", "Staff is mandatory");
            }
            if (StringUtils.isBlank(staffPermission.getRegisterId())) {
                throw new CustomException("REGISTER_ID", "Register id is mandatory");
            }

            if (StringUtils.isBlank(staffPermission.getUserId())) {
                throw new CustomException("USER_ID", "User id is mandatory");
            }

            registerIds.add(staffPermission.getRegisterId());
        }

        AttendanceRegisterSearchCriteria criteria = AttendanceRegisterSearchCriteria.builder().ids(registerIds)
                .tenantId(staffPermissionList.get(0).getTenantId()).build();

        //check if Staff user id exists in staff registry //TODO after staff registry implementation
/*        try{
            UUID uuid=UUID.fromString(staffPermission.getUserId());

        } catch (IllegalArgumentException exception){
            throw new CustomException("USER_ID", "USER_ID is not in UUID format");
        }*/


        //check if the register id exists
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        List<AttendanceRegister> getAttendanceRegisters = attendanceService.searchAttendanceRegister(requestInfoWrapper, criteria);
        if (getAttendanceRegisters.size() == 0) {
            throw new CustomException("REGISTER_ID", "Attendance Registers with given register ids does not exist for tenantId");
        }
//        AttendanceRegister attendanceRegister= getAttendanceRegisters.get(0);

        for (AttendanceRegister attendanceRegister : getAttendanceRegisters) {
            //check is staff user id exists in staff table. If yes check if the deenrollment date is not null
            List<StaffPermission> staffPermissionListForAttendanceRegister = attendanceRegister.getStaff();
            if (staffPermissionListForAttendanceRegister != null) {
                for (StaffPermission staffFromRequest : staffPermissionList) {
                    for (StaffPermission staffForAttendanceRegister : staffPermissionListForAttendanceRegister) {
                        if (staffFromRequest.getUserId().equals(staffForAttendanceRegister.getUserId())) {
                            staff_exists = true;
                            if (staffForAttendanceRegister.getDenrollmentDate() == null) {
                                staff_deenrolled = false;
                                break;
                            }
                        }
                        if (!staff_exists)
                            throw new CustomException("USER_ID", "Staff with the given user id is not linked with the given register id");
                        if (staff_deenrolled)
                            throw new CustomException("USER_ID", "Staff with the given user id is already de enrolled from the register");
                        if (staffPermissionList.size() <= 1)
                            errorMap.put("MIN_STAFF_REQUIRED", "Atleast one staff should be associated" +
                                    "with the register. Current number of staff in register : " + staffPermissionList.size());
                    }
                }
            }
        }
    }



    private void validateMDMSData(StaffPermission staffPermission, Object mdmsData, Map<String, String> errorMap) {
        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";

        List<Object> tenantRes = null;
        try {
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (CollectionUtils.isEmpty(tenantRes))
            errorMap.put("INVALID_TENANT", "The tenant: " + staffPermission.getTenantId() + " is not present in MDMS");
    }


}

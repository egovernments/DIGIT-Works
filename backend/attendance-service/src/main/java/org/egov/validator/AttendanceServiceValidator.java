package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.util.MDMSUtils;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterRequest;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.egov.web.models.StaffPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.util.AttendanceServiceConstants.MASTER_TENANTS;
import static org.egov.util.AttendanceServiceConstants.MDMS_TENANT_MODULE_NAME;

@Component
@Slf4j
public class AttendanceServiceValidator {

    @Autowired
    private MDMSUtils mdmsUtils;

    public void validateCreateAttendanceRegister(AttendanceRegisterRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        List<AttendanceRegister> attendanceRegisters = request.getAttendanceRegister();
        RequestInfo requestInfo = request.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateAttendanceRegisterRequest(attendanceRegisters, errorMap);

        String tenantId = attendanceRegisters.get(0).getTenantId();
        String rootTenantId = tenantId.split("\\.")[0];

        for (AttendanceRegister attendanceRegister : attendanceRegisters) {
            if (!attendanceRegister.getTenantId().equals(tenantId)) {
                throw new CustomException("MULTIPLE_TENANTS", "All registers must have same tenant Id. Please create new request for different tentant id");
            }
        }

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, rootTenantId);
        validateMDMSData(attendanceRegisters, mdmsData, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void validateUpdateAttendanceRegisterRequest(AttendanceRegisterRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        List<AttendanceRegister> attendanceRegisters = request.getAttendanceRegister();
        RequestInfo requestInfo = request.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateAttendanceRegisterRequest(attendanceRegisters, errorMap);

        String tenantId = attendanceRegisters.get(0).getTenantId();
        String rootTenantId = tenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, rootTenantId);
        validateMDMSData(attendanceRegisters, mdmsData, errorMap);

        for (int i = 0; i < attendanceRegisters.size(); i++) {
            if (attendanceRegisters.get(i).getId() == null) {
                errorMap.put("ATTENDANCE_REGISTER_ID", "Attendance register id is mandatory");
            }

            if (!attendanceRegisters.get(i).getTenantId().equals(tenantId)) {
                errorMap.put("MULTIPLE_TENANTS", "All registers must have same tenant Id. Please create new request for different tentant id");
            }
        }

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void validateUpdateAgainstDB(AttendanceRegisterRequest attendanceRegisterRequest, List<AttendanceRegister> attendanceRegisterList) {
        if (CollectionUtils.isEmpty(attendanceRegisterList) || attendanceRegisterRequest.getAttendanceRegister().size() != attendanceRegisterList.size()) {
            throw new CustomException("INVALID_REGISTER_MODIFY", "The record that you are trying to update does not exists in the system");
        }

        Set<String> registerIdsFromDB = attendanceRegisterList.stream().map(AttendanceRegister:: getId).collect(Collectors.toSet());
        for (AttendanceRegister attendanceRegister: attendanceRegisterRequest.getAttendanceRegister()) {
            if (!registerIdsFromDB.contains(attendanceRegister.getId())) {
                throw new CustomException("INVALID_REGISTER_MODIFY", "The register Id " + attendanceRegister.getId() + " that you are trying to update does not exists in the system");
            }

            if (attendanceRegister.getStaff() != null) {
                Set<String> staffIdsFromDB = attendanceRegister.getStaff().stream().map(StaffPermission:: getId).collect(Collectors.toSet());
                if (!staffIdsFromDB.contains(attendanceRegisterRequest.getRequestInfo().getUserInfo().getUuid())) {
                    throw new CustomException("INVALID_REGISTER_MODIFY", "The user " + attendanceRegisterRequest.getRequestInfo().getUserInfo().getUuid() + " does not have permission to modify the register");
                }
            }
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

    private void validateAttendanceRegisterRequest(List<AttendanceRegister> attendanceRegisters, Map<String, String> errorMap) {
        if (attendanceRegisters == null || attendanceRegisters.size() == 0) {
            throw new CustomException("ATTENDANCE_REGISTER", "Attendance Register is mandatory");
        }

        for (int i = 0; i < attendanceRegisters.size(); i++) {
            if (attendanceRegisters.get(i) == null) {
                throw new CustomException("ATTENDANCE_REGISTER", "Attendance Register is mandatory");
            }
            if (StringUtils.isBlank(attendanceRegisters.get(i).getTenantId())) {
                errorMap.put("TENANT_ID", "Tenant is mandatory");
            }
            if (StringUtils.isBlank(attendanceRegisters.get(i).getName())) {
                errorMap.put("NAME", "Name is mandatory");
            }
            if (attendanceRegisters.get(i).getStartDate() == null) {
                errorMap.put("START_DATE", "Start date is mandatory");
            }
            if (attendanceRegisters.get(i).getStartDate().compareTo(attendanceRegisters.get(i).getEndDate()) > 0) {
                errorMap.put("DATE", "Start date should be less than end date");
            }
        }
    }

    private void validateMDMSData(List<AttendanceRegister> attendanceRegisters, Object mdmsData, Map<String, String> errorMap) {

        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";

        List<Object> tenantRes = null;
        try {
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (CollectionUtils.isEmpty(tenantRes))
            errorMap.put("INVALID_TENANT", "The tenant: " + attendanceRegisters.get(0).getTenantId() + " is not present in MDMS");
    }

    public void validateSearchRegisterRequest(RequestInfoWrapper requestInfoWrapper, AttendanceRegisterSearchCriteria searchCriteria) {
        if (searchCriteria == null || requestInfoWrapper == null) {
            throw new CustomException("REGISTER_SEARCH_CRITERIA_REQUEST", "Register search criteria request is mandatory");
        }

        Map<String, String> errorMap = new HashMap<>();

        validateRequestInfo(requestInfoWrapper.getRequestInfo(),errorMap);

        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "Tenant is mandatory");
        }

        // Throw exception if required parameters are missing
        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void validateRegisterAgainstDB(List<String> registerIds, List<AttendanceRegister> attendanceRegisterListFromDB, String tenantId) {

        Set<String> uniqueRegisterIdsFromRequest = new HashSet<>(registerIds);

        Set<String> uniqueRegisterIdsFromDB = attendanceRegisterListFromDB.stream()
                .map(register -> register.getId()).collect(Collectors.toSet());

        //check if all register ids from request exist in db
        for (String idFromRequest : uniqueRegisterIdsFromRequest) {
            if (!uniqueRegisterIdsFromDB.contains(idFromRequest)) {
                log.error("Attendance Registers with register id : " + idFromRequest + " does not exist for tenantId");
                throw new CustomException("REGISTER_ID", "Attendance Registers with register id : " + idFromRequest + " does not exist for tenantId");
            }
        }

    }
}

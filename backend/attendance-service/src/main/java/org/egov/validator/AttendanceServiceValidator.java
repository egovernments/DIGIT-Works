package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.AttendanceRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.MDMSUtils;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterRequest;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.egov.util.AttendanceServiceConstants.MASTER_TENANTS;
import static org.egov.util.AttendanceServiceConstants.MDMS_TENANT_MODULE_NAME;

@Component
@Slf4j
public class AttendanceServiceValidator {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MDMSUtils mdmsUtils;

    public void validateCreateAttendanceRegister(AttendanceRegisterRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        List<AttendanceRegister> attendanceRegisters = request.getAttendanceRegister();
        RequestInfo requestInfo = request.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateAttendanceRegister(attendanceRegisters, errorMap);

        String rootTenantId = attendanceRegisters.get(0).getTenantId();
        //split the tenantId
        rootTenantId = rootTenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);
        validateMDMSData(attendanceRegisters, mdmsData, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void validateUpdateAttendanceRegister(AttendanceRegisterRequest request) {
        Map<String, String > errorMap = new HashMap<>();
        List<AttendanceRegister> attendanceRegisters = request.getAttendanceRegister();
        RequestInfo requestInfo = request.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateAttendanceRegister(attendanceRegisters, errorMap);

        for (int i = 0; i < attendanceRegisters.size(); i++) {
            UUID id = attendanceRegisters.get(i).getId();
            if (id ==null) {
                errorMap.put("ATTENDANCE_REGISTER_ID", "Attendance register id is mandatory");
            } else {
                AttendanceRegisterSearchCriteria searchCriteria = AttendanceRegisterSearchCriteria.builder().id(id.toString())
                            .tenantId(attendanceRegisters.get(i).getTenantId()).build();
                List<AttendanceRegister> attendanceRegisterList = attendanceRepository.getAttendanceRegister(searchCriteria);
                if (CollectionUtils.isEmpty(attendanceRegisterList)) {
                    throw new CustomException("INVALID_REGISTER_MODIFY", "The record that you are trying to update does not exists in the system");
                }
            }
        }

        String rootTenantId = attendanceRegisters.get(0).getTenantId();
        //split the tenantId
        rootTenantId = rootTenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);
        validateMDMSData(attendanceRegisters, mdmsData, errorMap);

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

    private void validateAttendanceRegister(List<AttendanceRegister> attendanceRegisters, Map<String, String> errorMap) {
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

}

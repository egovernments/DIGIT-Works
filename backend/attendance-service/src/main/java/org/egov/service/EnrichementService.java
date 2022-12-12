package org.egov.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.repository.AttendanceRepository;
import org.egov.repository.IdGenRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.AttendanceServiceUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnrichementService {

    @Autowired
    private AttendanceServiceUtil attendanceServiceUtil;
    @Autowired
    private IdGenRepository idGenRepository;
    @Autowired
    private AttendanceServiceConfiguration config;
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StaffService staffService;


    public void enrichCreateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        RequestInfo requestInfo = attendanceRegisterRequest.getRequestInfo();
        List<AttendanceRegister> attendanceRegisters = attendanceRegisterRequest.getAttendanceRegister();

        for (int i = 0; i < attendanceRegisters.size(); i++) {
            String rootTenantId = attendanceRegisters.get(i).getTenantId().split("\\.")[0];

            List<String> registerNumbers = getIdList(requestInfo, rootTenantId
                    , config.getIdgenAttendanceRegisterNumberName(), config.getIdgenAttendanceRegisterNumberFormat(), 1);

            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), attendanceRegisters.get(i),true);
            attendanceRegisters.get(i).setAuditDetails(auditDetails);
            attendanceRegisters.get(i).setId(UUID.randomUUID());

            if (registerNumbers != null && !registerNumbers.isEmpty()) {
                attendanceRegisters.get(i).setRegisterNumber(registerNumbers.get(0));
            }

            List<StaffPermission> staffList = new ArrayList<>();

            try {
                StaffPermissionRequest staffPermissionRequest = staffService.createAttendanceStaff(getStaffPermissionRequest(requestInfo, attendanceRegisters.get(i).getTenantId(), String.valueOf(attendanceRegisters.get(i).getId())));
                staffList.add(staffPermissionRequest.getStaff());
                attendanceRegisters.get(i).setStaff(staffList);
            } catch (Exception e) {
                throw new CustomException("CREATE_STAFF", "Error in creating staff");
            }
        }
    }

    public void enrichUpdateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        RequestInfo requestInfo = attendanceRegisterRequest.getRequestInfo();
        List<AttendanceRegister> attendanceRegistersInUpdateReq = attendanceRegisterRequest.getAttendanceRegister();

        for (int i = 0; i < attendanceRegistersInUpdateReq.size(); i++) {
            AttendanceRegisterSearchCriteria searchCriteria = AttendanceRegisterSearchCriteria.builder()
                    .id(attendanceRegistersInUpdateReq.get(i).getId().toString())
                    .tenantId(attendanceRegistersInUpdateReq.get(i).getTenantId()).build();
            AttendanceRegister attendanceRegisterFromDB = attendanceRepository.getAttendanceRegister(searchCriteria).get(0);
            attendanceRegistersInUpdateReq.get(i).setAuditDetails(attendanceRegisterFromDB.getAuditDetails());
            attendanceRegistersInUpdateReq.get(i).setAttendees(attendanceRegisterFromDB.getAttendees());
            attendanceRegistersInUpdateReq.get(i).setStaff(attendanceRegisterFromDB.getStaff());
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), attendanceRegistersInUpdateReq.get(i), false);
            attendanceRegistersInUpdateReq.get(i).setAuditDetails(auditDetails);
        }
    }

    private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey,
                                   String idformat, int count) {
        List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count).getIdResponses();

        if (CollectionUtils.isEmpty(idResponses))
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");

        return idResponses.stream()
                .map(IdResponse::getId).collect(Collectors.toList());
    }

    private StaffPermissionRequest getStaffPermissionRequest(RequestInfo requestInfo, String tenantId, String registerId) {
        StaffPermissionRequest staffPermissionRequest = new StaffPermissionRequest();
        StaffPermission staff = new StaffPermission();
        staff.setUserId(requestInfo.getUserInfo().getUuid());
        staff.setTenantId(tenantId);
        staff.setRegisterId(registerId);
        staffPermissionRequest.setRequestInfo(requestInfo);
        staffPermissionRequest.setStaff(staff);
        return staffPermissionRequest;
    }
}

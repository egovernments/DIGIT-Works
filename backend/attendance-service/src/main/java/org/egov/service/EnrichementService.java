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
        String rootTenantId = attendanceRegisters.get(0).getTenantId().split("\\.")[0];

        List<String> registerNumbers = getIdList(requestInfo, rootTenantId
                , config.getIdgenAttendanceRegisterNumberName(), config.getIdgenAttendanceRegisterNumberFormat(), attendanceRegisters.size());

        List<StaffPermission> staffPermissionList = new ArrayList<>();
        for (int i = 0; i < attendanceRegisters.size(); i++) {
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), null,true);
            attendanceRegisters.get(i).setAuditDetails(auditDetails);
            attendanceRegisters.get(i).setId(UUID.randomUUID());

            if (registerNumbers != null && !registerNumbers.isEmpty()) {
                attendanceRegisters.get(i).setRegisterNumber(registerNumbers.get(i));
            }

            StaffPermission staffPermission = StaffPermission.builder().userId(requestInfo.getUserInfo().getUuid())
                                                    .tenantId(attendanceRegisters.get(0).getTenantId())
                                                    .registerId(String.valueOf(attendanceRegisters.get(i).getId())).build();

            staffPermissionList.add(staffPermission);
        }

        createFirstStaff(requestInfo, staffPermissionList, attendanceRegisters);
    }

    public void enrichUpdateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest, List<AttendanceRegister> attendanceRegistersListFromDB) {
        RequestInfo requestInfo = attendanceRegisterRequest.getRequestInfo();
        List<AttendanceRegister> attendanceRegistersListInUpdateReq = attendanceRegisterRequest.getAttendanceRegister();

        for (AttendanceRegister attendanceRegisterInUpdateReq: attendanceRegistersListInUpdateReq) {
            String registerId = String.valueOf(attendanceRegisterInUpdateReq.getId());
            AttendanceRegister attendanceRegisterFromDB = attendanceRegistersListFromDB.stream().filter(ar -> registerId.equals(String.valueOf(ar.getId()))).findFirst().orElse(null);

            attendanceRegisterInUpdateReq.setRegisterNumber(attendanceRegisterFromDB.getRegisterNumber());
            attendanceRegisterInUpdateReq.setAttendees(attendanceRegisterFromDB.getAttendees());
            attendanceRegisterInUpdateReq.setStaff(attendanceRegisterFromDB.getStaff());
            attendanceRegisterInUpdateReq.setAuditDetails(attendanceRegisterFromDB.getAuditDetails());
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), attendanceRegisterFromDB.getAuditDetails(), false);
            attendanceRegisterInUpdateReq.setAuditDetails(auditDetails);
        }
    }

    private void createFirstStaff(RequestInfo requestInfo, List<StaffPermission> staffPermissionList, List<AttendanceRegister> attendanceRegisters) {
        StaffPermissionRequest staffPermissionRequest = StaffPermissionRequest.builder().requestInfo(requestInfo).staff(staffPermissionList).build();
        StaffPermissionRequest staffPermissionResponse;

        try {
            staffPermissionResponse = staffService.createAttendanceStaff(staffPermissionRequest);
        } catch (Exception e) {
            throw new CustomException("CREATE_STAFF", "Error in creating staff");
        }

        for (int i = 0; i < attendanceRegisters.size(); i++) {
            String registerId = String.valueOf(attendanceRegisters.get(i).getId());
            List<StaffPermission> staff = staffPermissionResponse.getStaff().stream().filter(st -> registerId.equals(st.getRegisterId())).collect(Collectors.toList());
            attendanceRegisters.get(i).setStaff(staff);
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
}

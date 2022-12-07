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


    public void enrichCreateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        RequestInfo requestInfo = attendanceRegisterRequest.getRequestInfo();
        List<AttendanceRegister> attendanceRegisters = attendanceRegisterRequest.getAttendanceRegister();
//        List<StaffPermission> staff = attendanceRegisters.get(0).getStaff();
//        List<IndividualEntry> attendees = attendanceRegisters.get(0).getAttendees();
        String rootTenantId = attendanceRegisters.get(0).getTenantId().split("\\.")[0];
        List<String> registerNumbers = getIdList(requestInfo, rootTenantId
                , config.getIdgenAttendanceRegisterNumberName(), config.getIdgenAttendanceRegisterNumberFormat(), attendanceRegisters.size());

        for (int i = 0; i < attendanceRegisters.size(); i++) {
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), attendanceRegisters.get(i),true);
            attendanceRegisters.get(i).setAuditDetails(auditDetails);
            attendanceRegisters.get(i).setId(UUID.randomUUID());

            if (registerNumbers != null && !registerNumbers.isEmpty()) {
                attendanceRegisters.get(i).setRegisterNumber(registerNumbers.get(i));
            }
        }
    }

    public void enrichUpdateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        RequestInfo requestInfo = attendanceRegisterRequest.getRequestInfo();
        //List<AttendanceRegister> attendanceRegisters = attendanceRegisterRequest.getAttendanceRegister();
        AttendanceRegister attendanceRegister= attendanceRegisterRequest.getAttendanceRegister().get(0);

        AttendanceRegisterSearchCriteria searchCriteria = AttendanceRegisterSearchCriteria.builder().id(attendanceRegister.getId().toString())
                .tenantId(attendanceRegister.getTenantId()).build();
        List<AttendanceRegister> attendanceRegisterList = attendanceRepository.getAttendanceRegister(searchCriteria);

        attendanceRegisterRequest.setAttendanceRegister(attendanceRegisterList);

        AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), attendanceRegister, false);
        attendanceRegister.setAuditDetails(auditDetails);
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

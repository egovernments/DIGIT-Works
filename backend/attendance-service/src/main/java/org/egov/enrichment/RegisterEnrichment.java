package org.egov.enrichment;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.AttendanceServiceConfiguration;
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
@Slf4j
public class RegisterEnrichment {

    @Autowired
    private AttendanceServiceUtil attendanceServiceUtil;
    @Autowired
    private IdGenRepository idGenRepository;
    @Autowired
    private AttendanceServiceConfiguration config;


    /* Enrich Attendance Register on Create Request */
    public void enrichCreateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        RequestInfo requestInfo = attendanceRegisterRequest.getRequestInfo();
        List<AttendanceRegister> attendanceRegisters = attendanceRegisterRequest.getAttendanceRegister();

        String rootTenantId = attendanceRegisters.get(0).getTenantId().split("\\.")[0];

        //Get Register Numbers from IdGen Service for number of registers present in AttendanceRegisters
        List<String> registerNumbers = getIdList(requestInfo, rootTenantId
                , config.getIdgenAttendanceRegisterNumberName(), "", attendanceRegisters.size()); //idFormat will be fetched by idGen service

        for (int i = 0; i < attendanceRegisters.size(); i++) {

            if (registerNumbers != null && !registerNumbers.isEmpty()) {
                attendanceRegisters.get(i).setRegisterNumber(registerNumbers.get(i));
            } else {
                throw new CustomException("ATTENDANCE_REGISTER_NUMBER_NOT_GENERATED","Error occurred while generating attendance register numbers from IdGen service");
            }

            //Enrich attendance register id and audit details
            attendanceRegisters.get(i).setId(UUID.randomUUID().toString());
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), null, true);
            attendanceRegisters.get(i).setAuditDetails(auditDetails);

        }
    }

    /* Enrich Attendance Register on Update Request */
    public void enrichUpdateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest, List<AttendanceRegister> attendanceRegistersListFromDB) {
        RequestInfo requestInfo = attendanceRegisterRequest.getRequestInfo();
        List<AttendanceRegister> attendanceRegistersListInUpdateReq = attendanceRegisterRequest.getAttendanceRegister();

        for (AttendanceRegister attendanceRegisterInUpdateReq : attendanceRegistersListInUpdateReq) {
            String registerId = String.valueOf(attendanceRegisterInUpdateReq.getId());
            AttendanceRegister attendanceRegisterFromDB = attendanceRegistersListFromDB.stream().filter(ar -> registerId.equals(String.valueOf(ar.getId()))).findFirst().orElse(null);

            // Set read only values i.e register number, attendees, staff to the attendance register update request as in attendance register from DB
            attendanceRegisterInUpdateReq.setRegisterNumber(attendanceRegisterFromDB.getRegisterNumber());
            attendanceRegisterInUpdateReq.setAttendees(attendanceRegisterFromDB.getAttendees());
            attendanceRegisterInUpdateReq.setStaff(attendanceRegisterFromDB.getStaff());

            // Set audit details for register update request
            attendanceRegisterInUpdateReq.setAuditDetails(attendanceRegisterFromDB.getAuditDetails());
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), attendanceRegisterFromDB.getAuditDetails(), false);
            attendanceRegisterInUpdateReq.setAuditDetails(auditDetails);
        }
    }

    /* Adds staff details to the associated attendance register */
    public void enrichStaffInRegister(List<AttendanceRegister> attendanceRegisters, StaffPermissionRequest staffPermissionResponse) {
        for (AttendanceRegister attendanceRegister : attendanceRegisters) {
            String registerId = String.valueOf(attendanceRegister.getId());
            List<StaffPermission> staff = staffPermissionResponse.getStaff().stream().filter(st -> registerId.equals(st.getRegisterId())).collect(Collectors.toList());
            attendanceRegister.setStaff(staff);
        }
    }

    /* Get id list from IdGen service */
    private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey,
                                   String idformat, int count) {
        List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count).getIdResponses();

        if (CollectionUtils.isEmpty(idResponses)) {
            log.error("No ids returned from idgen Service");
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");
        }

        return idResponses.stream()
                .map(IdResponse::getId).collect(Collectors.toList());
    }


    public void enrichSearchRegisterRequest(RequestInfo requestInfo, AttendanceRegisterSearchCriteria searchCriteria) {

        if (searchCriteria.getLimit() == null)
            searchCriteria.setLimit(config.getAttendanceRegisterDefaultLimit());

        if (searchCriteria.getOffset() == null)
            searchCriteria.setOffset(config.getAttendanceRegisterDefaultOffset());

        if (searchCriteria.getLimit() != null && searchCriteria.getLimit() > config.getAttendanceRegisterMaxLimit())
            searchCriteria.setLimit(config.getAttendanceRegisterMaxLimit());

    }
}

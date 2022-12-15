package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.producer.Producer;
import org.egov.repository.AttendanceRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendanceRegisterService {
    @Autowired
    private AttendanceServiceValidator attendanceServiceValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private Producer producer;

    @Autowired
    private AttendanceServiceConfiguration attendanceServiceConfiguration;

    @Autowired
    private EnrichementService enrichementService;

    @Autowired
    private AttendanceRepository repository;

    @Autowired
    private StaffService staffService;

    /**
     * Create Attendance register
     *
     * @param request
     * @return
     */
    public AttendanceRegisterRequest createAttendanceRegister(AttendanceRegisterRequest request) {
        attendanceServiceValidator.validateCreateAttendanceRegister(request);
        enrichementService.enrichCreateAttendanceRegister(request);
        StaffPermissionRequest staffPermissionResponse = staffService.createFirstStaff(request.getRequestInfo(), request.getAttendanceRegister());
        enrichementService.enrichStaffInRegister(request.getAttendanceRegister(), staffPermissionResponse);
        producer.push(attendanceServiceConfiguration.getSaveAttendanceRegisterTopic(), request);
        return request;
    }

    /**
     * Search attendace register based on given search criteria
     *
     * @param requestInfoWrapper
     * @param searchCriteria
     * @return
     */
    public List<AttendanceRegister> searchAttendanceRegister(RequestInfoWrapper requestInfoWrapper, AttendanceRegisterSearchCriteria searchCriteria) {

        attendanceServiceValidator.validateSearchEstimate(requestInfoWrapper,searchCriteria);
        List<AttendanceRegister> attendanceRegisterList = repository.getAttendanceRegister(searchCriteria);
        return attendanceRegisterList;
    }

    /**
     * Update the given attendance register details
     *
     * @param attendanceRegisterRequest
     * @return
     */
    public AttendanceRegisterRequest updateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        attendanceServiceValidator.validateUpdateAttendanceRegisterRequest(attendanceRegisterRequest);
        List<AttendanceRegister> attendanceRegistersFromDB = getAttendanceRegisters(attendanceRegisterRequest);
        attendanceServiceValidator.validateUpdateAgainstDB(attendanceRegisterRequest, attendanceRegistersFromDB);
        enrichementService.enrichUpdateAttendanceRegister(attendanceRegisterRequest, attendanceRegistersFromDB);
        producer.push(attendanceServiceConfiguration.getUpdateAttendanceRegisterTopic(), attendanceRegisterRequest);

        return attendanceRegisterRequest;
    }

    private List<AttendanceRegister> getAttendanceRegisters(AttendanceRegisterRequest request) {
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(request.getRequestInfo()).build();
        List<AttendanceRegister> attendanceRegisters = request.getAttendanceRegister();

        List<String> registerIds = new ArrayList<>();
        for (AttendanceRegister attendanceRegister: attendanceRegisters) {
            registerIds.add(String.valueOf(attendanceRegister.getId()));
        }

        AttendanceRegisterSearchCriteria searchCriteria = AttendanceRegisterSearchCriteria.builder().ids(registerIds)
                .tenantId(attendanceRegisters.get(0).getTenantId()).build();
        List<AttendanceRegister> attendanceRegisterList;
        try {
            attendanceRegisterList = searchAttendanceRegister(requestInfoWrapper, searchCriteria);
        } catch (Exception e) {
            throw new CustomException("SEARCH_ATTENDANCE_REGISTER", "Error in searching attendance register");
        }

        return attendanceRegisterList;
    }

}

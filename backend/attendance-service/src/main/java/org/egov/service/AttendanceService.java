package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterRequest;
import org.egov.web.models.AttendanceRegisterResponse;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class AttendanceService {
    @Autowired
    private AttendanceServiceValidator attendanceServiceValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;


    @Autowired
    private AttendanceRepository repository;

    /**
     * Create Attendance register
     *
     * @param attendanceRegisterRequest
     * @return
     */
    public AttendanceRegisterResponse createAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        //TODO Returning Dummy Response

        AttendanceRegister attendanceRegister = attendanceRegisterRequest.getAttendanceRegister();
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendanceRegisterRequest.getRequestInfo(), true);
        AttendanceRegisterResponse attendanceRegisterResponse = AttendanceRegisterResponse.builder().responseInfo(responseInfo).attendanceRegister(Collections.singletonList(attendanceRegister)).build();
        return attendanceRegisterResponse;
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
    public AttendanceRegisterResponse updateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        //TODO Returning Dummy Response

        AttendanceRegister attendanceRegister = attendanceRegisterRequest.getAttendanceRegister();
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendanceRegisterRequest.getRequestInfo(), true);
        AttendanceRegisterResponse attendanceRegisterResponse = AttendanceRegisterResponse.builder().responseInfo(responseInfo).attendanceRegister(Collections.singletonList(attendanceRegister)).build();
        return attendanceRegisterResponse;
    }
}

package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceLogServiceValidator;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class AttendanceLogService {
    @Autowired
    private AttendanceLogServiceValidator attendanceLogServiceValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    /**
     * Create Attendance Log
     *
     * @param attendanceLogRequest
     * @return
     */
    public AttendanceLogResponse createAttendanceLog(AttendanceLogRequest attendanceLogRequest) {
        //TODO Returning Dummy Response

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendanceLogRequest.getRequestInfo(), true);
        AttendanceLogResponse attendanceLogResponse = AttendanceLogResponse.builder().responseInfo(responseInfo).attendance(attendanceLogRequest.getAttendance()).build();
        return attendanceLogResponse;
    }

    /**
     * Search attendace logs based on given search criteria
     *
     * @param requestInfo
     * @param searchCriteria
     * @return
     */
    public AttendanceLogResponse searchAttendanceLog(RequestInfo requestInfo, AttendanceLogSearchCriteria searchCriteria) {
        //TODO Returning Dummy Response

        AttendanceLog attendanceLog = new AttendanceLog();
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true);
        AttendanceLogResponse attendanceLogResponse = AttendanceLogResponse.builder().responseInfo(responseInfo).attendance(Collections.singletonList(attendanceLog)).build();
        return attendanceLogResponse;
    }

    /**
     * Update the given attendance log details
     *
     * @param attendanceLogRequest
     * @return
     */
    public AttendanceLogResponse updateAttendanceLog(AttendanceLogRequest attendanceLogRequest) {
        //TODO Returning Dummy Response

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendanceLogRequest.getRequestInfo(), true);
        AttendanceLogResponse attendanceLogResponse = AttendanceLogResponse.builder().responseInfo(responseInfo).attendance(attendanceLogRequest.getAttendance()).build();
        return attendanceLogResponse;
    }
}

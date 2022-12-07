package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.producer.Producer;
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
    private Producer producer;

    @Autowired
    private AttendanceServiceConfiguration attendanceServiceConfiguration;

    @Autowired
    private EnrichementService enrichementService;

    /**
     * Create Attendance register
     *
     * @param request
     * @return
     */
    public AttendanceRegisterRequest createAttendanceRegister(AttendanceRegisterRequest request) {
        attendanceServiceValidator.validateCreateAttendanceRegister(request);
        enrichementService.enrichCreateAttendanceRegister(request);
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
        attendanceServiceValidator.validateUpdateAttendanceRegister(attendanceRegisterRequest);
        enrichementService.enrichUpdateAttendanceRegister(attendanceRegisterRequest);
        producer.push(attendanceServiceConfiguration.getUpdateAttendanceRegisterTopic(), attendanceRegisterRequest);

        return attendanceRegisterRequest;
    }
}

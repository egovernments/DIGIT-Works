package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.producer.Producer;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.StaffServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StaffService {
    @Autowired
    private StaffServiceValidator staffServiceValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private EnrichmentService enrichmentService;

    @Autowired
    private Producer producer;

    @Autowired
    private AttendanceServiceConfiguration serviceConfiguration;

    /**
     * Create attendance staff
     *
     * @param staffPermissionRequest
     * @return
     */
    public StaffPermissionRequest createAttendanceStaff(StaffPermissionRequest staffPermissionRequest) {
        staffServiceValidator.validateCreateStaffPermission(staffPermissionRequest);
        enrichmentService.enrichCreateStaffPermission(staffPermissionRequest);

        producer.push(serviceConfiguration.getSaveStaffTopic(), staffPermissionRequest);
        return staffPermissionRequest;
    }

    /**
     * Update(Soft Delete) the given attendance staff
     *
     * @param staffPermissionRequest
     * @return
     */
    public StaffPermissionRequest deleteAttendanceStaff(StaffPermissionRequest staffPermissionRequest) {
        staffServiceValidator.validateDeleteStaffPermission(staffPermissionRequest);
        enrichmentService.enrichDeleteStaffPermission(staffPermissionRequest);

        producer.push(serviceConfiguration.getUpdateStaffTopic(), staffPermissionRequest);
      /*  ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(staffPermissionRequest.getRequestInfo(), true);
        StaffPermissionResponse staffPermissionResponse = StaffPermissionResponse.builder().responseInfo(responseInfo).staff(staffPermissionRequest.getStaff()).build();
        return staffPermissionResponse;*/

        return  staffPermissionRequest;
    }
}

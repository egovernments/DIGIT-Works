package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
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

    /**
     * Create attendance staff
     *
     * @param staffPermissionRequest
     * @return
     */
    public StaffPermissionRequest createAttendanceStaff(StaffPermissionRequest staffPermissionRequest) {
            return staffPermissionRequest;
    }

    /**
     * Update(Soft Delete) the given attendance staff
     *
     * @param staffPermissionRequest
     * @return
     */
    public StaffPermissionResponse deleteAttendanceStaff(StaffPermissionRequest staffPermissionRequest) {
        //TODO Returning Dummy Response

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(staffPermissionRequest.getRequestInfo(), true);
        StaffPermissionResponse staffPermissionResponse = StaffPermissionResponse.builder().responseInfo(responseInfo).staff(staffPermissionRequest.getStaff()).build();
        return staffPermissionResponse;
    }
}

package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.tracer.model.CustomException;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.StaffServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        StaffPermissionResponse staffPermissionResponse = StaffPermissionResponse.builder().responseInfo(responseInfo).staff((StaffPermission) staffPermissionRequest.getStaff()).build();
        return staffPermissionResponse;
    }
    public StaffPermissionRequest createFirstStaff(RequestInfo requestInfo, List<AttendanceRegister> attendanceRegisters) {
        List<StaffPermission> staffPermissionList = new ArrayList<>();

        for (AttendanceRegister attendanceRegister: attendanceRegisters) {
            StaffPermission staffPermission = StaffPermission.builder().userId(requestInfo.getUserInfo().getUuid())
                    .tenantId(attendanceRegister.getTenantId())
                    .registerId(String.valueOf(attendanceRegister.getId())).build();

            staffPermissionList.add(staffPermission);
        }

        StaffPermissionRequest staffPermissionRequest = StaffPermissionRequest.builder().requestInfo(requestInfo).staff(staffPermissionList).build();
        StaffPermissionRequest staffPermissionResponse;

        try {
            staffPermissionResponse = createAttendanceStaff(staffPermissionRequest);
        } catch (Exception e) {
            throw new CustomException("CREATE_STAFF", "Error in creating staff");
        }

        return staffPermissionResponse;
    }
}

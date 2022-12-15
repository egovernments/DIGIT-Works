package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.producer.Producer;
import org.egov.repository.RegisterRepository;
import org.egov.repository.StaffRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.StaffServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private StaffRepository staffRepository;

    @Autowired
    private RegisterRepository registerRepository;

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
        //incoming createRequest validation
        staffServiceValidator.validateCreateStaffPermissionRequestParameters(staffPermissionRequest);

        //extract registerIds and staffUserIds from client request
        List<String> staffIds=new ArrayList<>();
        List<String> registerIds=new ArrayList<>();
        for(StaffPermission staffPermission:staffPermissionRequest.getStaffPermissionList()){
            staffIds.add(staffPermission.getUserId());
            registerIds.add(staffPermission.getRegisterId());
        }


        //db call to get the staffList data
        AttendanceStaffSearchCriteria staffSearchCriteria=AttendanceStaffSearchCriteria.builder().registerIds(registerIds).individualIds(staffIds).build();
        AttendanceRegisterSearchCriteria attendanceRegisterSearchCriteria=AttendanceRegisterSearchCriteria.builder().ids(registerIds).build();
        List<AttendanceRegister> attendanceRegisterList=registerRepository.getRegister(attendanceRegisterSearchCriteria);
        List<StaffPermission> staffPermissionList=staffRepository.getStaffFromRegisterIdsAndIndividualIds(staffSearchCriteria);


        //validator call by passing staff request and the data from db call
        staffServiceValidator.validateCreateStaffPermission(staffPermissionRequest,staffPermissionList,attendanceRegisterList);

        //enrichment call by passing staff request and data from db call


        //pus to producer


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

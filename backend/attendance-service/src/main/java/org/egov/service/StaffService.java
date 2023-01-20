package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.StaffEnrichmentService;
import org.egov.kafka.Producer;
import org.egov.repository.RegisterRepository;
import org.egov.repository.StaffRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.validator.StaffServiceValidator;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.StaffSearchCriteria;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
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

    @Autowired
    private StaffEnrichmentService staffEnrichmentService;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private RegisterRepository registerRepository;


    @Autowired
    private Producer producer;

    @Autowired
    private AttendanceServiceConfiguration serviceConfiguration;

    @Autowired
    private AttendanceRegisterService attendanceRegisterService;

    @Autowired
    private AttendanceServiceValidator attendanceServiceValidator;


    /**
     * Create attendance staff
     *
     * @param staffPermissionRequest
     * @return
     */
    public StaffPermissionRequest createAttendanceStaff(StaffPermissionRequest staffPermissionRequest, Boolean isFirstStaff) {
        //incoming createRequest validation
        staffServiceValidator.validateStaffPermissionRequestParameters(staffPermissionRequest);

        //extract registerIds and staffUserIds from client request
        String tenantId = staffPermissionRequest.getStaff().get(0).getTenantId();
        List<String> staffIds = extractStaffIdsFromRequest(staffPermissionRequest);
        List<String> registerIds = extractRegisterIdsFromRequest(staffPermissionRequest);

        //db call to get the staffList data whose de enrollment date is null
        StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIds).individualIds(staffIds).build();
        List<StaffPermission> staffPermissionListFromDB = getActiveStaff(staffSearchCriteria);

        //db call to get registers from db and use them to validate request registers
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(staffPermissionRequest.getRequestInfo()).build();
        if (!isFirstStaff) {
            List<AttendanceRegister> attendanceRegisterListFromDB = attendanceRegisterService.getAttendanceRegisters(requestInfoWrapper, registerIds, tenantId);
            attendanceServiceValidator.validateRegisterAgainstDB(registerIds, attendanceRegisterListFromDB, tenantId);

            //validator call by passing staff request and the data from db call
            staffServiceValidator.validateCreateStaffPermission(staffPermissionRequest, staffPermissionListFromDB, attendanceRegisterListFromDB);
        }

        //enrichment call by passing staff request and data from db call
        staffEnrichmentService.enrichCreateStaffPermission(staffPermissionRequest);

        //push to producer
        producer.push(serviceConfiguration.getSaveStaffTopic(), staffPermissionRequest);
        return staffPermissionRequest;
    }

    public List<StaffPermission> getActiveStaff(StaffSearchCriteria staffSearchCriteria){
        return staffRepository.getActiveStaff(staffSearchCriteria);
    }

    /**
     * Update(Soft Delete) the given attendance staff
     *
     * @param staffPermissionRequest
     * @return
     */
    public StaffPermissionRequest deleteAttendanceStaff(StaffPermissionRequest staffPermissionRequest) {
        //incoming deleteRequest validation
        staffServiceValidator.validateStaffPermissionRequestParameters(staffPermissionRequest);

        //extract registerIds and staffUserIds from client request
        String tenantId = staffPermissionRequest.getStaff().get(0).getTenantId();
        List<String> staffIds = extractStaffIdsFromRequest(staffPermissionRequest);
        List<String> registerIds = extractRegisterIdsFromRequest(staffPermissionRequest);

        //db call to get registers from db and use them to validate request registers
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(staffPermissionRequest.getRequestInfo()).build();
        List<AttendanceRegister> attendanceRegisterListFromDB = attendanceRegisterService.getAttendanceRegisters(requestInfoWrapper, registerIds, tenantId);
        attendanceServiceValidator.validateRegisterAgainstDB(registerIds, attendanceRegisterListFromDB, tenantId);

        // db call to get staff data
        StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIds).build();
        List<StaffPermission> staffPermissionListFromDB = getAllStaff(staffSearchCriteria);

        //validator call by passing staff request and the data from db call
        staffServiceValidator.validateDeleteStaffPermission(staffPermissionRequest, staffPermissionListFromDB, attendanceRegisterListFromDB);

        staffEnrichmentService.enrichDeleteStaffPermission(staffPermissionRequest, staffPermissionListFromDB);

        producer.push(serviceConfiguration.getUpdateStaffTopic(), staffPermissionRequest);
        return staffPermissionRequest;
    }

    public List<StaffPermission> getAllStaff(StaffSearchCriteria staffSearchCriteria){
        return staffRepository.getAllStaff(staffSearchCriteria);
    }

    /* Creates First Staff for the register, when register is created */
    public StaffPermissionRequest createFirstStaff(RequestInfo requestInfo, List<AttendanceRegister> attendanceRegisters) { //check enroll
        List<StaffPermission> staffPermissionList = new ArrayList<>();

        //Create StaffPermission for create staff request
        for (AttendanceRegister attendanceRegister : attendanceRegisters) {
            StaffPermission staffPermission = StaffPermission.builder().userId(requestInfo.getUserInfo().getUuid())
                    .tenantId(attendanceRegister.getTenantId())
                    .registerId(String.valueOf(attendanceRegister.getId())).build();

            staffPermissionList.add(staffPermission);
        }
        StaffPermissionRequest staffPermissionRequest = StaffPermissionRequest.builder().requestInfo(requestInfo).staff(staffPermissionList).build();
        StaffPermissionRequest staffPermissionResponse;

        // Calls create attendance staff with created request. If some error in creating staff, throws error
        try {
            staffPermissionResponse = createAttendanceStaff(staffPermissionRequest, true);
            log.info("Successfully created first staff for attendance register");
        } catch (Exception e) {
            log.error("Error in creating staff", e);
            throw new CustomException("CREATE_STAFF", "Error in creating staff");
        }
        return staffPermissionResponse;
    }

    private List<String> extractRegisterIdsFromRequest(StaffPermissionRequest staffPermissionRequest) {
        List<StaffPermission> staffPermissionListFromRequest = staffPermissionRequest.getStaff();
        List<String> registerIds = new ArrayList<>();
        for (StaffPermission staffPermission : staffPermissionListFromRequest) {
            registerIds.add(staffPermission.getRegisterId());
        }
        return registerIds;
    }

    private List<String> extractStaffIdsFromRequest(StaffPermissionRequest staffPermissionRequest) {
        List<StaffPermission> staffPermissionListFromRequest = staffPermissionRequest.getStaff();
        List<String> staffIds = new ArrayList<>();
        for (StaffPermission staffPermission : staffPermissionListFromRequest) {
            staffIds.add(staffPermission.getUserId());
        }
        return staffIds;
    }


}

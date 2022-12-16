package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.StaffEnrichmentService;
import org.egov.kafka.Producer;
import org.egov.repository.RegisterRepository;
import org.egov.repository.StaffRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.StaffServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Create attendance staff
     *
     * @param staffPermissionRequest
     * @return
     */
    public StaffPermissionRequest createAttendanceStaff(StaffPermissionRequest staffPermissionRequest) {
        //incoming createRequest validation
        staffServiceValidator.validateStaffPermissionRequestParameters(staffPermissionRequest);

        //extract registerIds and staffUserIds from client request
        List<String> staffIds=new ArrayList<>();
        List<String> registerIds=new ArrayList<>();
        List<StaffPermission> staffPermissionListFromRequest=staffPermissionRequest.getStaffPermissionList();
        String rootTenantId=staffPermissionRequest.getStaffPermissionList().get(0).getTenantId();
        for(StaffPermission staffPermission:staffPermissionListFromRequest){
            staffIds.add(staffPermission.getUserId());
            registerIds.add(staffPermission.getRegisterId());
        }

        //db call to get the staffList data whose de enrollment date is null
        AttendanceStaffSearchCriteria staffSearchCriteria=AttendanceStaffSearchCriteria.builder().registerIds(registerIds).individualIds(staffIds).build();
        List<StaffPermission> staffPermissionListFromDB=staffRepository.getActiveStaff(staffSearchCriteria);
        List<AttendanceRegister> attendanceRegisterListFromDB=getAttendanceRegisterList(registerIds,staffPermissionListFromRequest,rootTenantId);

        //validator call by passing staff request and the data from db call
        staffServiceValidator.validateCreateStaffPermission(staffPermissionRequest,staffPermissionListFromDB,attendanceRegisterListFromDB);

        //enrichment call by passing staff request and data from db call
        staffEnrichmentService.enrichCreateStaffPermission(staffPermissionRequest);

        //push to producer
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
        //incoming deleteRequest validation
        staffServiceValidator.validateStaffPermissionRequestParameters(staffPermissionRequest);

        //extract registerIds and staffUserIds from client request
        String rootTenantId=staffPermissionRequest.getStaffPermissionList().get(0).getTenantId();
        List<StaffPermission> staffPermissionListFromRequest=staffPermissionRequest.getStaffPermissionList();
        List<String> staffIds=new ArrayList<>();
        List<String> registerIds=new ArrayList<>();
        for(StaffPermission staffPermission:staffPermissionListFromRequest){
            staffIds.add(staffPermission.getUserId());
            registerIds.add(staffPermission.getRegisterId());
        }

        //db call to get the staffList data
        List<AttendanceRegister> attendanceRegisterListFromDB=getAttendanceRegisterList(registerIds,staffPermissionListFromRequest,rootTenantId);
        AttendanceStaffSearchCriteria staffSearchCriteria=AttendanceStaffSearchCriteria.builder().registerIds(registerIds).individualIds(staffIds).build();
        List<StaffPermission> staffPermissionListFromDB=staffRepository.getAllStaff(staffSearchCriteria);

        //validator call by passing staff request and the data from db call
        staffServiceValidator.validateDeleteStaffPermission(staffPermissionRequest,staffPermissionListFromDB,attendanceRegisterListFromDB);

        staffEnrichmentService.enrichDeleteStaffPermission(staffPermissionRequest,staffPermissionListFromDB);

        producer.push(serviceConfiguration.getUpdateStaffTopic(), staffPermissionRequest);
        return  staffPermissionRequest;
    }


    public List<AttendanceRegister> getAttendanceRegisterList(List<String> registerIds,List<StaffPermission> staffPermissionList,String tenantId){

        Set<String> uniqueRegisterIds=new HashSet<>(registerIds);
        boolean register_exists=false;
        AttendanceRegisterSearchCriteria attendanceRegisterSearchCriteria=AttendanceRegisterSearchCriteria
                .builder().ids(registerIds).tenantId(tenantId).build();
        List<AttendanceRegister> attendanceRegisterList=registerRepository.getRegister(attendanceRegisterSearchCriteria);

        List<String> registerIdsFromDB=attendanceRegisterList.stream().map(register->register.getId().toString())
                .collect(Collectors.toList());

        //check if all register ids exist in db
        for(String idFromRequest:uniqueRegisterIds){
            for(String idFromDB:registerIdsFromDB){
                if(idFromRequest.equals(idFromDB)){
                    register_exists=true;
                    break;
                }
            }
            if(!register_exists){
                throw new CustomException("REGISTER_ID", "Attendance Registers with register id : "+idFromRequest+" does not exist for tenantId");
            }
        }

        return attendanceRegisterList;
    }

}

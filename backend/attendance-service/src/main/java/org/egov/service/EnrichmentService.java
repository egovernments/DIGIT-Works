package org.egov.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.StaffServiceConfiguration;
import org.egov.util.StaffPermissionServiceUtil;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class EnrichmentService {

    @Autowired
    private StaffPermissionServiceUtil staffPermissionServiceUtil;

    @Autowired
    private StaffServiceConfiguration config;

    @Autowired
    private AttendanceService attendanceService;


    public void enrichCreateStaffPermission(StaffPermissionRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        StaffPermission staffPermission = request.getStaff();

        AuditDetails auditDetails = staffPermissionServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), staffPermission, true);
        staffPermission.setAuditDetails(auditDetails);
        staffPermission.setId(UUID.randomUUID());
        Date currentDT = new Date();
        BigDecimal enrollmentDate = new BigDecimal(currentDT.getTime());
        staffPermission.setEnrollmentDate(enrollmentDate.doubleValue());
    }

    public void enrichDeleteStaffPermission(StaffPermissionRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        StaffPermission staffPermission = request.getStaff();

        AttendanceRegisterSearchCriteria searchCriteria = AttendanceRegisterSearchCriteria.builder().id(staffPermission.getRegisterId())
                .tenantId(staffPermission.getTenantId()).build();

        RequestInfoWrapper requestInfoWrapper=RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        //Existing Staff List
        List<AttendanceRegister> checkAttendanceRegisters=attendanceService.searchAttendanceRegister(requestInfoWrapper,searchCriteria);
        List<StaffPermission> staffPermissionList = attendanceService.searchAttendanceRegister(requestInfoWrapper,searchCriteria).get(0).getStaff();

        for(StaffPermission staff:staffPermissionList){
            if(staff.getUserId().equals(staffPermission.getUserId()) && staff.getDenrollmentDate()==null){
                staffPermission.setId(staff.getId());
                staffPermission.setEnrollmentDate(staff.getEnrollmentDate());
            }
        }


        AuditDetails auditDetails = staffPermissionServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), staffPermission, true);

        staffPermission.setAuditDetails(auditDetails);

        Date currentDT = new Date();
        BigDecimal deenrollmentDate = new BigDecimal(currentDT.getTime());
        staffPermission.setDenrollmentDate(deenrollmentDate.doubleValue());
    }



}

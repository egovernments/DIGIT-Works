package org.egov.enrichment;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.service.AttendanceRegisterService;
import org.egov.util.StaffPermissionServiceUtil;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StaffEnrichmentService {

    @Autowired
    private StaffPermissionServiceUtil staffPermissionServiceUtil;

    @Autowired
    private AttendanceServiceConfiguration config;

    @Autowired
    private AttendanceRegisterService attendanceRegisterService;


    public void enrichCreateStaffPermission(StaffPermissionRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<StaffPermission> staffPermissionListFromRequest = request.getStaffPermissionList();

        for (StaffPermission staffPermissionFromRequest : staffPermissionListFromRequest) {
                    AuditDetails auditDetails = staffPermissionServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), staffPermissionFromRequest, true);
                    staffPermissionFromRequest.setAuditDetails(auditDetails);
                    staffPermissionFromRequest.setId(UUID.randomUUID());
                    Date currentDT = new Date();
                    BigDecimal enrollmentDate = new BigDecimal(currentDT.getTime());
                    staffPermissionFromRequest.setEnrollmentDate(enrollmentDate.doubleValue());
                }
    }

    public void enrichDeleteStaffPermission(StaffPermissionRequest request,List<StaffPermission> staffPermissionListFromDB) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<StaffPermission> staffPermissionListFromRequest = request.getStaffPermissionList();

        for(StaffPermission staffPermissionFromRequest:staffPermissionListFromRequest){
            for(StaffPermission staffPermissionFromDB:staffPermissionListFromDB) {
                if (staffPermissionFromDB.getUserId().equals(staffPermissionFromRequest.getUserId())
                        && staffPermissionFromDB.getRegisterId().equals(staffPermissionFromRequest.getRegisterId())
                        && staffPermissionFromDB.getDenrollmentDate() == 0) {
                    staffPermissionFromRequest.setId(staffPermissionFromDB.getId());
                    staffPermissionFromRequest.setEnrollmentDate(staffPermissionFromDB.getEnrollmentDate());

                    AuditDetails auditDetails = staffPermissionServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), staffPermissionFromDB, false);

                    staffPermissionFromRequest.setAuditDetails(auditDetails);

                    Date currentDT = new Date();
                    BigDecimal deenrollmentDate = new BigDecimal(currentDT.getTime());
                    staffPermissionFromRequest.setDenrollmentDate(deenrollmentDate.doubleValue());
                }
            }
        }



    }



}

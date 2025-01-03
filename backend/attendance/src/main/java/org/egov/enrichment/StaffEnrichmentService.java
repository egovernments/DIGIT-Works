package org.egov.enrichment;

import digit.models.coremodels.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Individual;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.util.AttendanceServiceUtil;
import org.egov.util.IndividualServiceUtil;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StaffEnrichmentService {

    private final AttendanceServiceUtil attendanceServiceUtil;
    private final IndividualServiceUtil individualServiceUtil;
    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public StaffEnrichmentService(AttendanceServiceUtil attendanceServiceUtil, IndividualServiceUtil individualServiceUtil, MultiStateInstanceUtil multiStateInstanceUtil) {
        this.attendanceServiceUtil = attendanceServiceUtil;
        this.individualServiceUtil = individualServiceUtil;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    public void enrichStaffPermissionOnCreate(StaffPermissionRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<StaffPermission> staffPermissionListFromRequest = request.getStaff();

        for (StaffPermission staffPermissionFromRequest : staffPermissionListFromRequest) {
            List <String> individualId = Collections.singletonList(staffPermissionFromRequest.getUserId());
            String tenantId = staffPermissionFromRequest.getTenantId();
            List<Individual> individualList = individualServiceUtil.getIndividualDetails(individualId, requestInfo, multiStateInstanceUtil.getStateLevelTenant(tenantId));
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), staffPermissionFromRequest.getAuditDetails(), true);
            staffPermissionFromRequest.setAuditDetails(auditDetails);
            staffPermissionFromRequest.setId(UUID.randomUUID().toString());
            BigDecimal enrollmentDate = new BigDecimal(System.currentTimeMillis());
            staffPermissionFromRequest.setEnrollmentDate(enrollmentDate);
            staffPermissionFromRequest.setAdditionalDetails(Map.of("staffName", individualList.get(0).getName().getGivenName()));
        }
    }

    public void enrichStaffPermissionOnDelete(StaffPermissionRequest request, List<StaffPermission> staffPermissionListFromDB) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<StaffPermission> staffPermissionListFromRequest = request.getStaff();

        for (StaffPermission staffPermissionFromRequest : staffPermissionListFromRequest) {
            for (StaffPermission staffPermissionFromDB : staffPermissionListFromDB) {
                if (staffPermissionFromDB.getUserId().equals(staffPermissionFromRequest.getUserId())
                        && staffPermissionFromDB.getRegisterId().equals(staffPermissionFromRequest.getRegisterId())
                        && staffPermissionFromDB.getDenrollmentDate() == null) {
                    staffPermissionFromRequest.setId(staffPermissionFromDB.getId());
                    staffPermissionFromRequest.setEnrollmentDate(staffPermissionFromDB.getEnrollmentDate());

                    AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), staffPermissionFromDB.getAuditDetails(), false);

                    staffPermissionFromRequest.setAuditDetails(auditDetails);

                    BigDecimal deenrollmentDate = new BigDecimal(System.currentTimeMillis());
                    staffPermissionFromRequest.setDenrollmentDate(deenrollmentDate);
                }
            }
        }


    }


}

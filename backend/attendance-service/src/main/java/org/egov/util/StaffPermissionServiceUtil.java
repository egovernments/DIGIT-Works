package org.egov.util;


import digit.models.coremodels.AuditDetails;
import org.egov.web.models.StaffPermission;
import org.springframework.stereotype.Component;

@Component
public class StaffPermissionServiceUtil {

    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, StaffPermission staffPermission, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(staffPermission.getAuditDetails().getCreatedBy()).lastModifiedBy(by)
                    .createdTime(staffPermission.getAuditDetails().getCreatedTime()).lastModifiedTime(time).build();
    }
}

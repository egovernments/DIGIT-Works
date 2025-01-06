package org.egov.util;

import digit.models.coremodels.AuditDetails;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AttendanceServiceUtil {
    public AuditDetails getAuditDetails(String by, AuditDetails auditDetails, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(auditDetails.getCreatedBy()).lastModifiedBy(by)
                    .createdTime(auditDetails.getCreatedTime()).lastModifiedTime(time).build();
    }
}

package org.egov.util;

import digit.models.coremodels.AuditDetails;
import org.egov.web.models.AttendanceRegister;
import org.springframework.stereotype.Component;

@Component
public class AttendanceServiceUtil {
    public AuditDetails getAuditDetails(String by, AttendanceRegister attendanceRegister, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(attendanceRegister.getAuditDetails().getCreatedBy()).lastModifiedBy(by)
                    .createdTime(attendanceRegister.getAuditDetails().getCreatedTime()).lastModifiedTime(time).build();
    }
}

package org.egov.util;


import digit.models.coremodels.AuditDetails;
import org.egov.web.models.MusterRoll;
import org.springframework.stereotype.Component;

@Component
public class MusterRollServiceUtil {

    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, MusterRoll musterRoll, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(musterRoll.getAuditDetails().getCreatedBy()).lastModifiedBy(by)
                    .createdTime(musterRoll.getAuditDetails().getCreatedTime()).lastModifiedTime(time).build();
    }
}

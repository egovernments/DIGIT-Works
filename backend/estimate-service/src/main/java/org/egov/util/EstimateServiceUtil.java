package org.egov.util;


import digit.models.coremodels.AuditDetails;
import org.egov.web.models.Estimate;
import org.springframework.stereotype.Component;

@Component
public class EstimateServiceUtil {

    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, Estimate estimate, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(estimate.getAuditDetails().getCreatedBy()).lastModifiedBy(by)
                    .createdTime(estimate.getAuditDetails().getCreatedTime()).lastModifiedTime(time).build();
    }
}

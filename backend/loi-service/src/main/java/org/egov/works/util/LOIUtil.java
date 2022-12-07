package org.egov.works.util;

import digit.models.coremodels.AuditDetails;
import org.egov.works.web.models.LetterOfIndent;
import org.springframework.stereotype.Component;

@Component
public class LOIUtil {

    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, LetterOfIndent letterOfIndent, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(letterOfIndent.getAuditDetails().getCreatedBy()).lastModifiedBy(by)
                    .createdTime(letterOfIndent.getAuditDetails().getCreatedTime()).lastModifiedTime(time).build();
    }

}

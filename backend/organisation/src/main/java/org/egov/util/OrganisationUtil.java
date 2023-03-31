package org.egov.util;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.web.models.Function;
import org.egov.web.models.Organisation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OrganisationUtil {
    /**
     * Method to set auditDetails for create/update flows of organisations
     *
     * @param by
     * @param isCreate
     * @return
     */
    public void setAuditDetailsForOrganisation(String by, List<Organisation> organisationList, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        for (Organisation organisation : organisationList) {
            if (isCreate) {
                AuditDetails auditDetailsForCreate = AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
                organisation.setAuditDetails(auditDetailsForCreate);
            } else {
                AuditDetails auditDetailsForUpdate = AuditDetails.builder().lastModifiedBy(by).lastModifiedTime(time).build();
                organisation.setAuditDetails(auditDetailsForUpdate);
            }
        }
    }

    /**
     * Method to set auditDetails for create/update flows of functions
     *
     * @param by
     * @param isCreate
     * @return
     */
    public void setAuditDetailsForFunction(String by, List<Function> functionList, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        for (Function function : functionList) {
            if (isCreate) {
                AuditDetails auditDetailsForCreate = AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
                function.setAuditDetails(auditDetailsForCreate);
            } else {
                AuditDetails auditDetailsForUpdate = AuditDetails.builder().lastModifiedBy(by).lastModifiedTime(time).build();
                function.setAuditDetails(auditDetailsForUpdate);
            }
        }
    }
}

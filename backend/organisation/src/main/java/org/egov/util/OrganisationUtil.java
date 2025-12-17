package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.OrganisationRepository;
import org.egov.web.models.Function;
import org.egov.web.models.OrgSearchCriteria;
import org.egov.web.models.OrgSearchRequest;
import org.egov.web.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OrganisationUtil {

    @Autowired
    private OrganisationRepository organisationRepository;
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
            if (Boolean.TRUE.equals(isCreate)) {
                AuditDetails auditDetailsForCreate = AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
                organisation.setAuditDetails(auditDetailsForCreate);
            } else {
                AuditDetails auditDetailsForUpdate = AuditDetails.builder().createdBy(organisation.getAuditDetails().getCreatedBy()).createdTime(organisation.getAuditDetails().getCreatedTime()).lastModifiedBy(by).lastModifiedTime(time).build();
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
            if (Boolean.TRUE.equals(isCreate)) {
                AuditDetails auditDetailsForCreate = AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
                function.setAuditDetails(auditDetailsForCreate);
            } else {
                AuditDetails auditDetailsForUpdate = AuditDetails.builder().lastModifiedBy(by).lastModifiedTime(time).build();
                function.setAuditDetails(auditDetailsForUpdate);
            }
        }
    }

    public List<Organisation> searchExistingOrganisation(RequestInfo requestInfo, Organisation organisation){
        log.info("Inside searchExistingOrganisation method");
        OrgSearchCriteria orgSearchCriteria = OrgSearchCriteria.builder().tenantId(requestInfo.getUserInfo().getTenantId()).orgNumber(organisation.getOrgNumber()).build();
        OrgSearchRequest orgSearchRequest = OrgSearchRequest.builder().requestInfo(requestInfo).searchCriteria(orgSearchCriteria).build();
        return organisationRepository.getOrganisations(orgSearchRequest);

    }
}

package org.egov.helper;

import org.egov.web.models.ApplicationStatus;
import org.egov.web.models.Organisation;

import java.math.BigDecimal;
import java.util.Collections;

public class OrganisationTestBuilder {
    private Organisation.OrganisationBuilder builder;

    public OrganisationTestBuilder(){
        this.builder = Organisation.builder();
    }

    public static OrganisationTestBuilder builder(){
        return new OrganisationTestBuilder();
    }

    public Organisation build(){
        return this.builder.build();
    }

    public OrganisationTestBuilder addGoodOrganisation(){
        this.builder
                .id("org-id-1")
                .tenantId("t1")
                .applicationNumber("application-number")
                .orgNumber("org-number")
                .name("Organisation-1")
                .applicationStatus(ApplicationStatus.ACTIVE)
                .externalRefNumber("External-ref_no")
                .dateOfIncorporation(BigDecimal.valueOf(System.currentTimeMillis()))
                .orgAddress(Collections.singletonList(AddressTestBuilder.builder().addGoodAddress().build()))
                .contactDetails(Collections.singletonList(ContactDetailsTestBuilder.builder().addGoodContactDetailsForOrg().build()))
                .identifiers(Collections.singletonList(IdentifierTestBuilder.builder().addGoodIdentifierForOrg().build()))
                .functions(Collections.singletonList(FunctionTestBuilder.builder().addGoodFunctionForOrg().build()))
                .jurisdiction(Collections.singletonList(JurisdictionTestBuilder.builder().addGoodJurisdictionForOrg().build()))
                .isActive(true)
                .documents(Collections.singletonList(DocumentTestBuilder.builder().addGoodDocumentForOrg().build()))
                .additionalDetails(null)
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        return this;
    }

    public OrganisationTestBuilder addGoodOrganisationWithoutIds(){
        this.builder
                .tenantId("t1")
                .applicationNumber("application-number")
                .orgNumber("org-number")
                .name("Organisation-1")
                .applicationStatus(ApplicationStatus.ACTIVE)
                .externalRefNumber("External-ref_no")
                .dateOfIncorporation(BigDecimal.valueOf(System.currentTimeMillis()))
                .orgAddress(Collections.singletonList(AddressTestBuilder.builder().addGoodAddressWithoutId().build()))
                .contactDetails(Collections.singletonList(ContactDetailsTestBuilder.builder().addGoodContactDetailsWithoutIdForOrg().build()))
                .identifiers(Collections.singletonList(IdentifierTestBuilder.builder().addGoodIdentifierWithoutIdForOrg().build()))
                .functions(Collections.singletonList(FunctionTestBuilder.builder().addGoodFunctionWithoutIdForOrg().build()))
                .jurisdiction(Collections.singletonList(JurisdictionTestBuilder.builder().addGoodJurisdictionWithoutIdForOrg().build()))
                .isActive(true)
                .documents(Collections.singletonList(DocumentTestBuilder.builder().addGoodDocumentForOrg().build()))
                .additionalDetails(null)
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        return this;
    }

}

package org.egov.helper;

import org.egov.web.models.ApplicationStatus;
import org.egov.web.models.Function;
import org.egov.web.models.Jurisdiction;

import java.math.BigDecimal;
import java.util.Collections;

public class FunctionTestBuilder {

    private Function.FunctionBuilder builder;

    public FunctionTestBuilder(){
        this.builder = Function.builder();
    }

    public Function build(){
        return this.builder.build();
    }

    public static FunctionTestBuilder builder(){
        return new FunctionTestBuilder();
    }

    public FunctionTestBuilder addGoodFunctionForOrg(){
        this.builder
                .id("identifier-id-1")
                .orgId("org-id-1")
                .applicationNumber("app-no-1")
                .type("VEN.CMS")
                .category("VEN.CW")
                .propertyClass("A")
                .validFrom(BigDecimal.valueOf(System.currentTimeMillis()))
                .validTo(BigDecimal.valueOf(System.currentTimeMillis()))
                .applicationStatus(ApplicationStatus.ACTIVE)
                .wfStatus(null)
                .isActive(true)
                .documents(Collections.singletonList(DocumentTestBuilder.builder().addGoodDocumentForOrgFunction().build()))
                .additionalDetails(null)
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        return this;
    }

    public FunctionTestBuilder addGoodFunctionWithoutIdForOrg(){
        this.builder
                .orgId("org-id-1")
                .applicationNumber("app-no-1")
                .type("VEN.CMS")
                .category("VEN.CW")
                .propertyClass("NA")
                .validFrom(BigDecimal.valueOf(System.currentTimeMillis()))
                .validTo(BigDecimal.valueOf(System.currentTimeMillis()))
                .applicationStatus(ApplicationStatus.ACTIVE)
                .wfStatus(null)
                .isActive(true)
                .documents(Collections.singletonList(DocumentTestBuilder.builder().addGoodDocumentForOrgFunction().build()))
                .additionalDetails(null)
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        return this;
    }
}

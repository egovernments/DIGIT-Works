package org.egov.helper;

import org.egov.web.models.ApplicationStatus;
import org.egov.web.models.Function;
import org.egov.web.models.Jurisdiction;

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
                .type("OrgType-1.CD")
                .category("FUN.CD")
                .propertyClass("A")
                .validFrom(1680527922000d)
                .validTo(2532604722000d)
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
                .type("OrgType-1.CD")
                .category("FUN.CD")
                .propertyClass("A")
                .validFrom(1680527922000d)
                .validTo(2532604722000d)
                .applicationStatus(ApplicationStatus.ACTIVE)
                .wfStatus(null)
                .isActive(true)
                .documents(Collections.singletonList(DocumentTestBuilder.builder().addGoodDocumentForOrgFunction().build()))
                .additionalDetails(null)
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        return this;
    }
}

package org.egov.works.helper;

import org.egov.works.web.models.Target;

public class TargetTestBuilder {

    private Target.TargetBuilder builder;

    public TargetTestBuilder(){
        this.builder = Target.builder();
    }

    public static TargetTestBuilder builder(){
        return new TargetTestBuilder();
    }

    public Target build(){
        return this.builder.build();
    }

    public TargetTestBuilder addGoodTarget(){
        this.builder
                .id("Id-1")
                .projectid("ProjectId-1")
                .beneficiaryType("Slum")
                .totalNo(1)
                .targetNo(1)
                .isDeleted(false)
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        return this;

    }

    public TargetTestBuilder addTargetWithoutIdAndAuditDetails(){
        this.builder
                .projectid("ProjectId-1")
                .beneficiaryType("BeneficiaryType-1")
                .totalNo(1)
                .targetNo(1)
                .isDeleted(false);

        return this;

    }
}

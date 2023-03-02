package org.egov.works.helper;

import org.egov.works.web.models.AdditionalFields;
import org.egov.works.web.models.Project;

import java.util.Collections;

public class ProjectTestBuilder {
    private Project.ProjectBuilder builder;

    public ProjectTestBuilder(){
        this.builder = Project.builder();
    }

    public static ProjectTestBuilder builder(){
        return new ProjectTestBuilder();
    }

    public Project build(){
        return this.builder.build();
    }

    public ProjectTestBuilder addGoodProject(){
        this.builder
                .id("Project-1")
                .tenantId("t1")
                .projectNumber("ProjectNumber-1")
                .name("ProjectName-1")
                .projectType("MP-CWS")
                .projectSubType("MP001")
                .department("DEPT_11")
                .description("Description")
                .referenceID("ReferenceId-1")
                .documents(Collections.singletonList(DocumentTestBuilder.builder().addGoodDocument().build()))
                .address(AddressTestBuilder.builder().addGoodAddress().build())
                .startDate(Long.valueOf(1L))
                .endDate(Long.valueOf(2L))
                .isTaskEnabled(true)
                .natureOfWork("Capital")
                .targets(Collections.singletonList(TargetTestBuilder.builder().addGoodTarget().build()))
                .isDeleted(false)
                .rowVersion(Integer.valueOf(1))
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build())
                .additionalDetails(AdditionalFields.builder().build());

        return this;
    }

    public ProjectTestBuilder addBadProject(){
        this.builder
                .id("Project-1")
                .projectNumber("ProjectNumber-1")
                .name("ProjectName-1")
                .projectType("ProjectType-1")
                .projectSubType("ProjectSubType-1")
                .department("Department-1")
                .description("Description")
                .referenceID("ReferenceId-1")
                .documents(Collections.singletonList(DocumentTestBuilder.builder().addGoodDocument().build()))
                .address(AddressTestBuilder.builder().addGoodAddress().build())
                .startDate(Long.valueOf(1L))
                .endDate(Long.valueOf(2L))
                .isTaskEnabled(true)
                .parent("Parent-1")
                .natureOfWork("Capital")
                .targets(Collections.singletonList(TargetTestBuilder.builder().addGoodTarget().build()))
                .isDeleted(false)
                .rowVersion(Integer.valueOf(1))
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build())
                .additionalDetails(AdditionalFields.builder().build());

        return this;
    }


    public ProjectTestBuilder addProjectWithoutIdProjectNameAndAuditDetails(){
        this.builder
                .tenantId("state.city")
                .name("ProjectName-1")
                .projectType("ProjectType-1")
                .projectSubType("ProjectSubType-1")
                .department("Department-1")
                .description("Description")
                .referenceID("ReferenceId-1")
                .documents(Collections.singletonList(DocumentTestBuilder.builder().addDocumentWithoutIdAndAuditDetails().build()))
                .address(AddressTestBuilder.builder().addAddressWithoutIdAndAuditDetails().build())
                .startDate(Long.valueOf(1L))
                .endDate(Long.valueOf(2L))
                .isTaskEnabled(true)
                .parent("Project-1")
                .natureOfWork("Capital")
                .targets(Collections.singletonList(TargetTestBuilder.builder().addTargetWithoutIdAndAuditDetails().build()))
                .isDeleted(false)
                .rowVersion(Integer.valueOf(1))
                .additionalDetails(AdditionalFields.builder().build());

        return this;
    }

    public ProjectTestBuilder addGoodProjectForSearch(){
        this.builder
                .id("Project-1")
                .tenantId("t1")
                .projectNumber("ProjectNumber-1")
                .name("ProjectName-1")
                .projectType("MP-CWS")
                .projectSubType("MP001")
                .startDate(Long.valueOf(1L))
                .endDate(Long.valueOf(2L));

        return this;
    }

    public ProjectTestBuilder addBadProjectForSearch(){
        this.builder
                .id("")
                .tenantId("t1")
                .projectNumber("")
                .name("")
                .projectType("")
                .projectSubType("")
                .startDate(0L)
                .endDate(0L);

        return this;
    }
}

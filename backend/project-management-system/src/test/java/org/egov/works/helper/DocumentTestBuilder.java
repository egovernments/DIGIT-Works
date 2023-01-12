package org.egov.works.helper;

import org.egov.works.web.models.AdditionalFields;
import org.egov.works.web.models.Document;


public class DocumentTestBuilder {
    private Document.DocumentBuilder builder;

    public DocumentTestBuilder(){
        this.builder = Document.builder();
    }

    public Document build(){
        return this.builder.build();
    }

    public static DocumentTestBuilder builder(){
        return new DocumentTestBuilder();
    }

    public DocumentTestBuilder addGoodDocument(){
        this.builder
                .id("Id-1")
                .projectid("ProjectId-1")
                .fileStore("FileStore-1")
                .documentUid("DocumentUid-1")
                .status("Status")
                .additionalDetails(AdditionalFields.builder().build())
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        return this;
    }

    public DocumentTestBuilder addDocumentWithoutIdAndAuditDetails(){
        this.builder
                .projectid("ProjectId-1")
                .fileStore("FileStore-1")
                .documentUid("DocumentUid-1")
                .status("Status")
                .additionalDetails(AdditionalFields.builder().build());

        return this;
    }
}

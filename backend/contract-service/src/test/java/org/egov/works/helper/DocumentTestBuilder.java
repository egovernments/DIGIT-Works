package org.egov.works.helper;

import org.egov.works.web.models.Document;
import org.egov.works.web.models.Status;

public class DocumentTestBuilder {
    private Document.DocumentBuilder builder;

    public DocumentTestBuilder() {
        this.builder = Document.builder();
    }

    public Document build() {
        return this.builder.build();
    }

    public static DocumentTestBuilder builder() {
        return new DocumentTestBuilder();
    }

    public DocumentTestBuilder addGoodDocument() {
        this.builder
                .id("Id-1")
                .fileStore("FileStore-1")
                .documentType("some-documentType")
                .documentUid("DocumentUid-1")
                .additionalDetails(new Object())
                .status(Status.fromValue("ACTIVE"))
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build())
                .additionalDetails(AdditionalFields.builder().build());


        return this;
    }

    public DocumentTestBuilder addDocumentWithoutId() {
        this.builder
                .fileStore("FileStore-1")
                .documentUid("DocumentUid-1")
                .additionalDetails(new Object())
                .status(Status.fromValue("ACTIVE"))
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build())
                .additionalDetails(AdditionalFields.builder().build());

        return this;
    }

}

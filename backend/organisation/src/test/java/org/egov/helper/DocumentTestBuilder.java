package org.egov.helper;


import org.egov.web.models.Document;

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

    public DocumentTestBuilder addGoodDocumentForOrg(){
        this.builder
                .id("docId-1")
                .orgId("org-id-1")
                .fileStore("FileStore-1")
                .documentUid("DocumentUid-1")
                .documentType("DocumentType-1")
                .orgFunctionId(null)
                .additionalDetails(null);
        return this;
    }

    public DocumentTestBuilder addGoodDocumentForOrgFunction(){
        this.builder
                .id("docId-1")
                .orgId(null)
                .fileStore("FileStore-1")
                .documentUid("DocumentUid-1")
                .documentType("DocumentType-1")
                .orgFunctionId("org-1-func-1")
                .additionalDetails(null);
        return this;
    }

    public DocumentTestBuilder addDocumentWithoutIdForOrg(){
        this.builder
                .orgId("org-id-1")
                .fileStore("FileStore-1")
                .documentUid("DocumentUid-1")
                .documentType("DocumentType-1")
                .orgFunctionId(null)
                .additionalDetails(null);
        return this;
    }

    public DocumentTestBuilder addDocumentWithoutIdForOrgFunc(){
        this.builder
                .orgId(null)
                .fileStore("FileStore-1")
                .documentUid("DocumentUid-1")
                .documentType("DocumentType-1")
                .orgFunctionId("org-1-func-1")
                .additionalDetails(null);
        return this;
    }
}

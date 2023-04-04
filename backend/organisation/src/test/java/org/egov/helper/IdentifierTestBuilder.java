package org.egov.helper;

import org.egov.web.models.ContactDetails;
import org.egov.web.models.Identifier;

public class IdentifierTestBuilder {
    private Identifier.IdentifierBuilder builder;

    public IdentifierTestBuilder(){
        this.builder = Identifier.builder();
    }

    public Identifier build(){
        return this.builder.build();
    }

    public static IdentifierTestBuilder builder(){
        return new IdentifierTestBuilder();
    }

    public IdentifierTestBuilder addGoodIdentifierForOrg(){
        this.builder
                .id("identifier-id-1")
                .orgId("org-id-1")
                .type("TI1")
                .value("value-1")
                .additionalDetails(null);
        return this;
    }

    public IdentifierTestBuilder addGoodIdentifierWithoutIdForOrg(){
        this.builder
                .orgId("org-id-1")
                .type("TI1")
                .value("value-1")
                .additionalDetails(null);
        return this;
    }
}

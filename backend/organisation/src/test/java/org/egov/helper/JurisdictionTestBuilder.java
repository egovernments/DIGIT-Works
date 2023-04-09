package org.egov.helper;

import org.egov.web.models.Identifier;
import org.egov.web.models.Jurisdiction;

public class JurisdictionTestBuilder {
    private Jurisdiction.JurisdictionBuilder builder;

    public JurisdictionTestBuilder(){
        this.builder = Jurisdiction.builder();
    }

    public Jurisdiction build(){
        return this.builder.build();
    }

    public static JurisdictionTestBuilder builder(){
        return new JurisdictionTestBuilder();
    }

    public JurisdictionTestBuilder addGoodJurisdictionForOrg(){
        this.builder
                .id("j-id-1")
                .orgId("org-id-1")
                .code("code-1")
                .additionalDetails(null);
        return this;
    }

    public JurisdictionTestBuilder addGoodJurisdictionWithoutIdForOrg(){
        this.builder
                .orgId("org-id-1")
                .code("code-1")
                .additionalDetails(null);
        return this;
    }
}

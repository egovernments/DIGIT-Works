package org.egov.helper;


import org.egov.web.models.ContactDetails;

public class ContactDetailsTestBuilder {
    private ContactDetails.ContactDetailsBuilder builder;

    public ContactDetailsTestBuilder(){
        this.builder = ContactDetails.builder();
    }

    public ContactDetails build(){
        return this.builder.build();
    }

    public static ContactDetailsTestBuilder builder(){
        return new ContactDetailsTestBuilder();
    }

    public ContactDetailsTestBuilder addGoodContactDetailsForOrg(){
        this.builder
                .id("cd-id-1")
                .orgId("org-id-1")
                .tenantId("t1")
                .contactName("cname")
                .contactMobileNumber("00000")
                .contactEmail("a@b.com")
                .active(true)
                .roles(null)
                .type("TP1")
                .createdBy("user-1")
                .createdDate(1000000L)
                .lastModifiedBy("user-1")
                .lastModifiedDate(1000000L);
        return this;
    }

    public ContactDetailsTestBuilder addGoodContactDetailsWithoutIdForOrg(){
        this.builder
                .orgId("org-id-1")
                .tenantId("t1")
                .contactName("cname")
                .contactMobileNumber("00000")
                .contactEmail("a@b.com")
                .active(true)
                .roles(null)
                .type("TP1")
                .createdBy("user-1")
                .createdDate(1000000L)
                .lastModifiedBy("user-1")
                .lastModifiedDate(1000000L);
        return this;
    }
}

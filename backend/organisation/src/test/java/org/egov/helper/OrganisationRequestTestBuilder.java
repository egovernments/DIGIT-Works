package org.egov.helper;

import org.egov.web.models.OrgRequest;
import org.egov.web.models.Organisation;

import java.util.ArrayList;

public class OrganisationRequestTestBuilder {

    private OrgRequest.OrgRequestBuilder builder ;

    private ArrayList<Organisation> organisations = new ArrayList();

    public OrganisationRequestTestBuilder(){
        this.builder = OrgRequest.builder();
    }

     public static OrganisationRequestTestBuilder builder(){
         return new OrganisationRequestTestBuilder();
     }

     public OrgRequest build(){
         return this.builder.build();
     }

     public OrganisationRequestTestBuilder withRequestInfo(){
         this.builder.requestInfo(RequestInfoTestBuilder.builder().withCompleteRequestInfo().build());
         return this;
     }

     public OrganisationRequestTestBuilder addGoodOrganisationForCreate(){
         organisations.add(OrganisationTestBuilder.builder().addGoodOrganisationWithoutIds().build());
         this.builder.organisations(organisations);
         return this;
     }

    public OrganisationRequestTestBuilder addGoodOrganisationWithIds(){
        organisations.add(OrganisationTestBuilder.builder().addGoodOrganisation().build());
        this.builder.organisations(organisations);
        return this;
    }
}

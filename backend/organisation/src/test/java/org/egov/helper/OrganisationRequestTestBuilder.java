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

    // public static ProjectRequestTestBuilder builder(){
    //     return new ProjectRequestTestBuilder();
    // }

    // public ProjectRequest build(){
    //     return this.builder.build();
    // }

    // public ProjectRequestTestBuilder withRequestInfo(){
    //     this.builder.requestInfo(RequestInfoTestBuilder.builder().withCompleteRequestInfo().build());
    //     return this;
    // }

    // public ProjectRequestTestBuilder addGoodProject(){
    //     projects.add(ProjectTestBuilder.builder().addGoodProject().build());
    //     this.builder.projects(projects);
    //     return this;
    // }
}

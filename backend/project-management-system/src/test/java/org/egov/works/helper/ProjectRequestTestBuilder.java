package org.egov.works.helper;

import org.egov.common.helper.RequestInfoTestBuilder;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.ProjectRequest;

import java.util.ArrayList;

public class ProjectRequestTestBuilder {
    private ProjectRequest.ProjectRequestBuilder builder ;

    private ArrayList<Project> projects = new ArrayList();

    public ProjectRequestTestBuilder(){
        this.builder = ProjectRequest.builder();
    }

    public static ProjectRequestTestBuilder builder(){
        return new ProjectRequestTestBuilder();
    }

    public ProjectRequest build(){
        return this.builder.build();
    }

    public ProjectRequestTestBuilder withRequestInfo(){
        this.builder.requestInfo(RequestInfoTestBuilder.builder().withCompleteRequestInfo().build());
        return this;
    }

    public ProjectRequestTestBuilder addGoodProject(){
        projects.add(ProjectTestBuilder.builder().addGoodProject().build());
        this.builder.projects(projects);
        return this;
    }

    public ProjectRequestTestBuilder addProjectWithoutIdAndAuditDetails(){
        projects.add(ProjectTestBuilder.builder().addProjectWithoutIdProjectNameAndAuditDetails().build());
        this.builder.projects(projects);
        return this;
    }
}

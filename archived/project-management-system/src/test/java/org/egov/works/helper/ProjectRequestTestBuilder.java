package org.egov.works.helper;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.ProjectRequest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ProjectRequestTestBuilder addBadProject(){
        projects.add(ProjectTestBuilder.builder().addBadProject().build());
        this.builder.projects(projects);
        return this;
    }

    public ProjectRequestTestBuilder addGoodProjectForSearch(){
        projects.add(ProjectTestBuilder.builder().addGoodProjectForSearch().build());
        this.builder.projects(projects);
        return this;
    }

    public ProjectRequestTestBuilder addBadProjectForSearch(){
        projects.add(ProjectTestBuilder.builder().addBadProjectForSearch().build());
        this.builder.projects(projects);
        return this;
    }

    public ProjectRequestTestBuilder addProjectWithoutIdAndAuditDetails(){
        projects.add(ProjectTestBuilder.builder().addProjectWithoutIdProjectNameAndAuditDetails().build());
        this.builder.projects(projects);
        return this;
    }

    public ProjectRequestTestBuilder withProjectForCreateValidationSuccess(){
        projects.add(ProjectTestBuilder.builder().addGoodProject().build());
        this.builder.projects(projects).requestInfo(getRequestInfo());
        return this;
    }

    public ProjectRequestTestBuilder withMultipleProjectForCreateValidationSuccess(){
        projects.add(ProjectTestBuilder.builder().addGoodProject().build());
        projects.add(ProjectTestBuilder.builder().addGoodProject().build());
        projects.get(1).setName("Project-2");
        this.builder.projects(projects).requestInfo(getRequestInfo());
        return this;
    }

    public RequestInfo getRequestInfo(){
        Role role = new Role(1L,"JUNIOR ENGINEER","JUNIOR_ENGINEER","t1");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        User userInfo = User.builder().id(172L).uuid("5ce80dd3-b1c0-42fd-b8f6-a2be456db31c").userName("8070102021").name("test3").mobileNumber("8070102021")
                .emailId("xyz@egovernments.org").type("EMPLOYEE").roles(roles).build();
        RequestInfo requestInfo = RequestInfo.builder().apiId("project-management-system").msgId("create project").userInfo(userInfo).build();
        return requestInfo;
    }

    public ResponseInfo getResponseInfo_Success(ProjectRequest projectRequest) {
        ResponseInfo responseInfo = ResponseInfo.builder()
                .apiId(projectRequest.getRequestInfo().getApiId())
                .ver(projectRequest.getRequestInfo().getVer())
                .ts(projectRequest.getRequestInfo().getTs())
                .resMsgId("uief87324")
                .msgId(projectRequest.getRequestInfo().getMsgId())
                .status("successful").build();
        return responseInfo;
    }

    public Map<String, Object> getSearchProjectParams() {
        Map<String, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put("limit", 5);
        searchParamsMap.put("offset", 0);
        searchParamsMap.put("tenantId", "t1");
        searchParamsMap.put("lastChangedSince", null);
        searchParamsMap.put("includeDeleted", false);
        searchParamsMap.put("includeAncestors", false);
        searchParamsMap.put("includeDescendants", false);
        searchParamsMap.put("createdFrom", null);
        searchParamsMap.put("createdTo", null);

        return searchParamsMap;
    }



}

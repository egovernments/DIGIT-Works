package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.works.service.ProjectService;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.egov.common.contract.response.ResponseInfo;
import digit.models.coremodels.RequestInfoWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Controller
@RequestMapping("/project")
public class ProjectApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    public ProjectApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<ProjectResponse> createProject(@ApiParam(value = "Details for the new Project.", required = true) @Valid @RequestBody ProjectRequest project) {
        ProjectRequest enrichedProjectRequest = projectService.createProject(project);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(project.getRequestInfo(), true);
        ProjectResponse projectResponse = ProjectResponse.builder().responseInfo(responseInfo).project(enrichedProjectRequest.getProjects()).build();
        return new ResponseEntity<ProjectResponse>(projectResponse,HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<ProjectResponse> searchProject(@ApiParam(value = "Details for the project.", required = true) @Valid @RequestBody ProjectRequest project, @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset, @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince, @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted, @ApiParam(value = "Used in project search API to specify if response should include project elements that are in the preceding hierarchy of matched projects.", defaultValue = "false") @Valid @RequestParam(value = "includeAncestors", required = false, defaultValue = "false") Boolean includeAncestors,  @ApiParam(value = "Used in project search API to specify if response should include project elements that are in the following hierarchy of matched projects.", defaultValue = "false") @Valid @RequestParam(value = "includeDescendants", required = false, defaultValue = "false") Boolean includeDescendants, @ApiParam(value = "Used in project search API to limit the search results to only those projects whose creation date is after the specified 'createdFrom' date", defaultValue = "false") @Valid @RequestParam(value = "createdFrom", required = false) Long createdFrom, @ApiParam(value = "Used in project search API to limit the search results to only those projects whose creation date is before the specified 'createdTo' date", defaultValue = "false") @Valid @RequestParam(value = "createdTo", required = false) Long createdTo) {
        List<Project> projects = projectService.searchProject(project, limit, offset, tenantId, lastChangedSince, includeDeleted, includeAncestors, includeDescendants, createdFrom, createdTo);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(project.getRequestInfo(), true);
        Integer count = projectService.countAllProjects(project, tenantId, lastChangedSince, includeDeleted, createdFrom, createdTo);
        ProjectResponse projectResponse = ProjectResponse.builder().responseInfo(responseInfo).project(projects).totalCount(count).build();
        return new ResponseEntity<ProjectResponse>(projectResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<ProjectResponse> updateProject(@ApiParam(value = "Details for the new Project.", required = true) @Valid @RequestBody ProjectRequest project) {
        ProjectRequest enrichedProjectRequest = projectService.updateProject(project);

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(project.getRequestInfo(), true);
        ProjectResponse projectResponse = ProjectResponse.builder().responseInfo(responseInfo).project(enrichedProjectRequest.getProjects()).build();
        return new ResponseEntity<ProjectResponse>(projectResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/beneficiary/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<BeneficiaryResponse> projectBeneficiaryV1CreatePost(@ApiParam(value = "Capture details of benificiary type.", required = true) @Valid @RequestBody BeneficiaryRequest beneficiary) {
        return new ResponseEntity<BeneficiaryResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/beneficiary/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<BeneficiaryResponse> projectBeneficiaryV1SearchPost(@ApiParam(value = "Project Beneficiary Search.", required = true) @Valid @RequestBody BeneficiaryRequest beneficiary, @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset, @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince, @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) {
        return new ResponseEntity<BeneficiaryResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/beneficiary/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<BeneficiaryResponse> projectBeneficiaryV1UpdatePost(@ApiParam(value = "Project Beneficiary Registration.", required = true) @Valid @RequestBody BeneficiaryRequest beneficiary, @ApiParam(value = "Client can specify if the resource in request body needs to be sent back in the response. This is being used to limit amount of data that needs to flow back from the server to the client in low bandwidth scenarios. Server will always send the server generated id for validated requests.", defaultValue = "true") @Valid @RequestParam(value = "echoResource", required = false, defaultValue = "true") Boolean echoResource) {
        return new ResponseEntity<BeneficiaryResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/facility/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<ProjectFacilityResponse> projectFacilityV1CreatePost(@ApiParam(value = "Capture linkage of Project and facility.", required = true) @Valid @RequestBody ProjectFacilityRequest projectFacility) {
        return new ResponseEntity<ProjectFacilityResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/facility/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<ProjectFacilityResponse> projectFacilityV1SearchPost(@ApiParam(value = "Capture linkage of Project and facility.", required = true) @Valid @RequestBody ProjectFacilityRequest projectFacility, @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset, @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince, @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) {
        return new ResponseEntity<ProjectFacilityResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/facility/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<ProjectFacilityResponse> projectFacilityV1UpdatePost(@ApiParam(value = "Capture linkage of Project and facility.", required = true) @Valid @RequestBody ProjectFacilityRequest projectFacility) {
        return new ResponseEntity<ProjectFacilityResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/resource/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<ProjectResourceResponse> projectResourceV1CreatePost(@ApiParam(value = "Capture linkage of Project and resources.", required = true) @Valid @RequestBody ProjectResourceRequest projectResource) {
        return new ResponseEntity<ProjectResourceResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/resource/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<ProjectResourceResponse> projectResourceV1SearchPost(@ApiParam(value = "Search linkage of Project and resource.", required = true) @Valid @RequestBody ProjectResourceRequest projectResource, @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset, @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince, @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) {
        return new ResponseEntity<ProjectResourceResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/resource/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<ProjectResourceResponse> projectResourceV1UpdatePost(@ApiParam(value = "Capture linkage of Project and Resource.", required = true) @Valid @RequestBody ProjectResourceRequest projectResource) {
        return new ResponseEntity<ProjectResourceResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/staff/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<ProjectStaffResponse> projectStaffV1CreatePost(@ApiParam(value = "Capture linkage of Project and staff user.", required = true) @Valid @RequestBody ProjectStaffRequest projectStaff) {
        return new ResponseEntity<ProjectStaffResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/staff/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<ProjectStaffResponse> projectStaffV1SearchPost(@ApiParam(value = "Capture linkage of Project and staff user.", required = true) @Valid @RequestBody ProjectStaffRequest projectStaff, @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset, @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince, @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) {
        return new ResponseEntity<ProjectStaffResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/staff/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<ProjectStaffResponse> projectStaffV1UpdatePost(@ApiParam(value = "Capture linkage of Project and staff user.", required = true) @Valid @RequestBody ProjectStaffRequest projectStaff) {
        return new ResponseEntity<ProjectStaffResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/task/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<TaskResponse> projectTaskV1CreatePost(@ApiParam(value = "Capture details of Task", required = true) @Valid @RequestBody TaskRequest task) {
        return new ResponseEntity<TaskResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/task/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<TaskResponse> projectTaskV1SearchPost(@ApiParam(value = "Project Task Search.", required = true) @Valid @RequestBody TaskRequest task, @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset, @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince, @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) {
        return new ResponseEntity<TaskResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/task/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<TaskResponse> projectTaskV1UpdatePost(@ApiParam(value = "Capture details of Existing task", required = true) @Valid @RequestBody TaskRequest task) {
        return new ResponseEntity<TaskResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}

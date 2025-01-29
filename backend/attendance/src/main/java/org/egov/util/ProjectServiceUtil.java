package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.http.client.ServiceRequestClient;
import org.egov.common.models.project.Project;
import org.egov.common.models.project.ProjectRequest;
import org.egov.common.models.project.ProjectResponse;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static org.egov.util.AttendanceServiceConstants.LIMIT_OFFSET;

@Component
@Slf4j
public class ProjectServiceUtil {

	private final AttendanceServiceConfiguration config;
	private final ServiceRequestClient serviceRequestClient;

	@Autowired
	public ProjectServiceUtil(AttendanceServiceConfiguration config, ServiceRequestClient serviceRequestClient) {
		this.config = config;
		this.serviceRequestClient = serviceRequestClient;
	}

	/**
	 * Gets the List of Project based on the searchCriteria
	 * @param tenantId
	 * @param project
	 * @param requestInfo
	 * @param childProject
	 * @param isAncestorProjectId
	 * @return List
	 */
	public List<Project> getProject(String tenantId, Project project, RequestInfo requestInfo, Boolean childProject, boolean isAncestorProjectId){

		StringBuilder url = getProjectURL(tenantId, childProject, isAncestorProjectId);
		ProjectRequest projectRequest = ProjectRequest.builder().projects(Collections.singletonList(project)).requestInfo(requestInfo).build();

		try {
			ProjectResponse projectResponse = serviceRequestClient.fetchResult(url,projectRequest,ProjectResponse.class);
			return projectResponse != null ? projectResponse.getProject() : Collections.emptyList();
		} catch (Exception e) {
			log.error("Error while fetching project details: ", e);
			throw new CustomException("FETCH_PROJECT_ERROR", "Error while fetching project details");
		}
	}
	/**
	 * Builds Project search URL
	 * @param tenantId
	 * @param childRequired
	 * @param isAncestorProjectId
	 * @return URL
	 */
	public StringBuilder getProjectURL(String tenantId, Boolean childRequired, boolean isAncestorProjectId)
	{
		StringBuilder builder = new StringBuilder(config.getProjectHost());
		builder.append(config.getProjectSearchEndpoint()).append(LIMIT_OFFSET);
		builder.append("&tenantId=").append(tenantId);
		builder.append("&isAncestorProjectId=").append(isAncestorProjectId);
		if(childRequired!=null) builder.append("&includeDescendants=").append(childRequired);
		return builder;
	}

}

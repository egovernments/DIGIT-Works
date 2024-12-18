package org.egov.util;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.http.client.ServiceRequestClient;
import org.egov.common.models.project.Project;
import org.egov.common.models.project.ProjectRequest;
import org.egov.common.models.project.ProjectResponse;
import org.egov.config.AttendanceServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.egov.util.AttendanceServiceConstants.LIMIT_OFFSET;

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
	 * @return
	 */
	public List<Project> getProject(String tenantId, Project project, RequestInfo requestInfo){

		StringBuilder url = getProjectURL(tenantId);
		ProjectRequest projectRequest = ProjectRequest.builder().projects(Collections.singletonList(project)).requestInfo(requestInfo).build();
		ProjectResponse projectResponse = serviceRequestClient.fetchResult(url,projectRequest,ProjectResponse.class);

		return projectResponse.getProject();

	}
	/**
	 * Builds Project search URL
	 * @param tenantId
	 * @return URL
	 */
	public StringBuilder getProjectURL(String tenantId)
	{
		StringBuilder builder = new StringBuilder(config.getProjectHost());
		builder.append(config.getProjectSearchEndpoint()).append(LIMIT_OFFSET);
		builder.append("&tenantId=").append(tenantId);
		return builder;
	}

}

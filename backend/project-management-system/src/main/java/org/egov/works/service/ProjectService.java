package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.ProjectConfiguration;
import org.egov.works.enrichment.ProjectEnrichment;
import org.egov.works.producer.Producer;
import org.egov.works.repository.ProjectRepository;
import org.egov.works.validator.ProjectValidator;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProjectService {

    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private ProjectEnrichment projectEnrichment;

    @Autowired
    private Producer producer;

    @Autowired
    private ProjectConfiguration projectConfiguration;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectRequest createProject(ProjectRequest project) {
        projectValidator.validateCreateProjectRequest(project);
        projectEnrichment.enrichCreateProject(project);
        producer.push(projectConfiguration.getSaveProjectTopic(), project);
        return project;
    }

    public List<Project> searchProject(ProjectRequest project, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted) {
        projectValidator.valiateSearchProject(project, limit, offset, tenantId);
        List<Project> projects = projectRepository.getProjects(project, limit, offset, tenantId, lastChangedSince, includeDeleted);
        return projects;
    }

    public ProjectRequest updateProject(ProjectRequest project) {
        projectValidator.validateUpdateProjectRequest(project);
        List<Project> projectsFromDB = searchProject(getSearchProjectRequestForUpdate(project), projectConfiguration.getMaxLimit(), projectConfiguration.getMaxOffset(), project.getProjects().get(0).getTenantId(), null, false);
        projectValidator.validateUpdateAgainstDB(project.getProjects(), projectsFromDB);
        projectEnrichment.enrichUpdateProject(project, projectsFromDB);
        producer.push(projectConfiguration.getUpdateProjectTopic(), project);

        return project;
    }

    private ProjectRequest getSearchProjectRequestForUpdate(ProjectRequest projectRequest) {
        List<Project> projects = new ArrayList<>();
        for (Project project: projectRequest.getProjects()) {
            Project newProject = Project.builder()
                    .id(project.getId())
                    .tenantId(project.getTenantId())
                    .build();
            projects.add(newProject);
        }
        return ProjectRequest.builder()
                .requestInfo(projectRequest.getRequestInfo())
                .projects(projects)
                .build();
    }
}

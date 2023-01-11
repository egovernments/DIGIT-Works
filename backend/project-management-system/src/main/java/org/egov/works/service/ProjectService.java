package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.ProjectConfiguration;
import org.egov.works.enrichment.ProjectEnrichment;
import org.egov.works.producer.ProjectProducer;
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
    private ProjectProducer producer;

    @Autowired
    private ProjectConfiguration projectConfiguration;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectRequest createProject(ProjectRequest project) {
        projectValidator.validateCreateProjectRequest(project);
        projectEnrichment.enrichProjectOnCreate(project);
        log.info("Enriched with Project Number, Ids and AuditDetails");
        producer.push(projectConfiguration.getSaveProjectTopic(), project);
        log.info("Pushed to kafka");
        return project;
    }

    public List<Project> searchProject(ProjectRequest project, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted) {
        projectValidator.validateSearchProjectRequest(project, limit, offset, tenantId);
        List<Project> projects = projectRepository.getProjects(project, limit, offset, tenantId, lastChangedSince, includeDeleted);
        return projects;
    }

    public ProjectRequest updateProject(ProjectRequest project) {
        projectValidator.validateUpdateProjectRequest(project);
        log.info("Update project request validated");
        //Search projects based on project ids
        List<Project> projectsFromDB = searchProject(getSearchProjectRequestForUpdate(project), projectConfiguration.getMaxLimit(), projectConfiguration.getMaxOffset(), project.getProjects().get(0).getTenantId(), null, false);
        log.info("Fetched projects for update request");
        //Validate Update project request against projects fetched form database
        projectValidator.validateUpdateAgainstDB(project.getProjects(), projectsFromDB);
        projectEnrichment.enrichProjectOnUpdate(project, projectsFromDB);
        log.info("Enriched with project Number, Ids and AuditDetails");
        producer.push(projectConfiguration.getUpdateProjectTopic(), project);
        log.info("Pushed to kafka");

        return project;
    }

    /* Construct Project Request object for search which contains project id and tenant Id */
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

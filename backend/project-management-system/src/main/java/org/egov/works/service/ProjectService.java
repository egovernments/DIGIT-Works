package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.ProjectConfiguration;
import org.egov.works.enrichment.ProjectEnrichment;
import org.egov.works.producer.Producer;
import org.egov.works.validator.ProjectValidator;
import org.egov.works.web.models.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ProjectRequest createProject(ProjectRequest project) {
        projectValidator.validateCreateProjectRequest(project);
        projectEnrichment.enrichCreateProject(project);
        producer.push(projectConfiguration.getSaveProjectTopic(), project);
        return project;
    }
}

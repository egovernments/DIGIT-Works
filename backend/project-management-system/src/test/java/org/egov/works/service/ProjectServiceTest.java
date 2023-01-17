package org.egov.works.service;

import org.egov.works.config.ProjectConfiguration;
import org.egov.works.enrichment.ProjectEnrichment;
import org.egov.works.helper.ProjectRequestTestBuilder;
import org.egov.works.producer.ProjectProducer;
import org.egov.works.repository.ProjectRepository;
import org.egov.works.validator.ProjectValidator;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.ProjectRequest;
import org.egov.works.web.models.ProjectResponse;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectValidator projectValidator;

    @Mock
    private ProjectEnrichment projectEnrichment;

    @Mock
    private ProjectProducer producer;

    @Mock
    private ProjectConfiguration projectConfiguration;

    @Test
    public void shouldCreateProjectSuccessfully(){
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProject().build();
        when(projectConfiguration.getSaveProjectTopic()).thenReturn("save-project");

        projectService.createProject(projectRequest);

        verify(projectValidator, times(1)).validateCreateProjectRequest(projectRequest);

        verify(projectEnrichment, times(1)).enrichProjectOnCreate(projectRequest, null);

        verify(producer, times(1)).push(eq("save-project"), any(ProjectRequest.class));

        assertNotNull(projectRequest.getProjects());

    }

}

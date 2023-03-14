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

import java.util.List;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Mock
    private ProjectRepository projectRepository;

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

    @Test
    public void shouldSearchProjectSuccessfully(){
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        List<Project> searchProjectResults = ProjectRequestTestBuilder.builder().addGoodProject().build().getProjects();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        when(projectRepository.getProjects(projectRequest, (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId"), (Long)searchParams.get("lastChangedSince"), (Boolean) searchParams.get("includeDeleted")
                , (Boolean) searchParams.get("includeAncestors"), (Boolean) searchParams.get("includeDescendants"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")))
                .thenReturn(searchProjectResults);

        List<Project> response = projectService.searchProject(projectRequest, (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId"), (Long)searchParams.get("lastChangedSince"), (Boolean) searchParams.get("includeDeleted")
                , (Boolean) searchParams.get("includeAncestors"), (Boolean) searchParams.get("includeDescendants"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo"));

        verify(projectValidator, times(1)).validateSearchProjectRequest(projectRequest, (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo"));

        assertEquals(1, response.size());

        assertNotNull(response);

    }

}

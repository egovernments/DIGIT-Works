package org.egov.works.enrichment;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdGenerationResponse;
import digit.models.coremodels.IdResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.config.ProjectConfiguration;
import org.egov.works.helper.ProjectRequestTestBuilder;
import org.egov.works.repository.IdGenRepository;
import org.egov.works.util.ProjectServiceUtil;
import org.egov.works.web.models.ProjectRequest;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ProjectEnrichmentTest {

    @InjectMocks
    private ProjectEnrichment projectEnrichment;

    @Mock
    private ProjectServiceUtil projectServiceUtil;

    @Mock
    private IdGenRepository idGenRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ProjectConfiguration config;

    @Test
    public void enrichCreateProjectTest(){
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addProjectWithoutIdAndAuditDetails().build();

        when(config.getIdgenProjectNumberName()).thenReturn("project.number");
        when(config.getIdgenProjectNumberFormat()).thenReturn("PR/[fy:yyyy-yy]/[cy:MM]/[SEQ_PROJECT_NUM]");

        List<IdResponse> idList = new ArrayList<>();
        idList.add(IdResponse.builder().id("ProjectNumber-1").build());
        IdGenerationResponse idGenerationResponse = IdGenerationResponse.builder().idResponses(idList).responseInfo(ResponseInfo.builder().build()).build();
        lenient().when(idGenRepository.getId(any(RequestInfo.class),any(String.class),any(String.class),any(String.class),anyInt()))
                .thenReturn(idGenerationResponse);


        projectEnrichment.enrichCreateProject(projectRequest);

        assertNotNull(projectRequest.getProjects().get(0).getId());
        assertNotNull(projectRequest.getProjects().get(0).getProjectNumber());
        assertNotNull(projectRequest.getProjects().get(0).getAddress().getId());
        assertNotNull(projectRequest.getProjects().get(0).getDocuments().get(0).getId());
        assertNotNull(projectRequest.getProjects().get(0).getTargets().get(0).getId());
        verify(projectServiceUtil,times(1)).getAuditDetails(any(String.class),nullable(AuditDetails.class),eq(Boolean.TRUE));
    }
}

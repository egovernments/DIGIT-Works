package org.egov.works.enrichment;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdGenerationResponse;
import digit.models.coremodels.IdResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ProjectConfiguration;
import org.egov.works.helper.ProjectRequestTestBuilder;
import org.egov.works.repository.IdGenRepository;
import org.egov.works.util.ProjectServiceUtil;
import org.egov.works.web.models.Project;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ProjectEnrichmentTest {

    @InjectMocks
    private ProjectEnrichment projectEnrichment;

    @Mock
    private IdGenRepository idGenRepository;

    @Mock
    private ProjectConfiguration config;

    @Mock
    private ProjectServiceUtil projectServiceUtil;

    @Test
    public void shouldGenerateProjectNumber_IfSuccess(){
        idGenResponseSuccess();
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addProjectWithoutIdAndAuditDetails().build();
        projectRequest.getProjects().get(0).setTenantId("t1");

        projectEnrichment.enrichProjectOnCreate(projectRequest, null);
        assertNotNull(projectRequest.getProjects().get(0).getProjectNumber());
    }

    @Test
    public void shouldGenerateProjectHierarchy_IfSuccess(){
        idGenResponseSuccess();
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addProjectWithoutIdAndAuditDetails().build();
        List<Project> parentProjects = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProject().build().getProjects();
        projectRequest.getProjects().get(0).setTenantId("t1");

        projectEnrichment.enrichProjectOnCreate(projectRequest, parentProjects);
        String actualProjectHierarchy = parentProjects.get(0).getId() + "." + projectRequest.getProjects().get(0).getId();
        assertNotNull(projectRequest.getProjects().get(0).getProjectHierarchy());
        assertEquals(projectRequest.getProjects().get(0).getProjectHierarchy(), actualProjectHierarchy);
    }

    @Test
    public void shouldGenerateIds_IfSuccess() {
        idGenResponseSuccess();
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addProjectWithoutIdAndAuditDetails().build();

        projectEnrichment.enrichProjectOnCreate(projectRequest, null);

        assertNotNull(projectRequest.getProjects().get(0).getId());
        assertNotNull(projectRequest.getProjects().get(0).getAddress().getId());
        assertNotNull(projectRequest.getProjects().get(0).getDocuments().get(0).getId());
        assertNotNull(projectRequest.getProjects().get(0).getTargets().get(0).getId());
    }

    @Test
    public void shouldThrowException_IfIdgenFailed(){
        idGenResponseFailure();
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addProjectWithoutIdAndAuditDetails().build();
        CustomException exception = assertThrows(CustomException.class, ()-> projectEnrichment.enrichProjectOnCreate(projectRequest, null));
        assertTrue(exception.getCode().contentEquals("IDGEN ERROR"));
    }

    void idGenResponseSuccess() {
        //MOCK Idgen Response
        when(config.getIdgenProjectNumberName()).thenReturn("project.number");
        IdResponse idResponse = IdResponse.builder().id("PR/2022-23/01/000070").build();
        List<IdResponse> idResponses = new ArrayList<>();
        idResponses.add(idResponse);
        IdGenerationResponse idGenerationResponse = IdGenerationResponse.builder().idResponses(idResponses).responseInfo(ResponseInfo.builder().build()).build();
        lenient().when(idGenRepository.getId(any(RequestInfo.class),any(String.class),any(String.class),any(String.class),anyInt()))
                .thenReturn(idGenerationResponse);
    }



    void idGenResponseFailure() {
        //MOCK Idgen Response
        when(config.getIdgenProjectNumberName()).thenReturn("project.number");
        List<IdResponse> idResponses = new ArrayList<>();
        IdGenerationResponse idGenerationResponse = IdGenerationResponse.builder().idResponses(idResponses).build();
        lenient().when(idGenRepository.getId(any(RequestInfo.class),any(String.class),any(String.class),any(String.class),anyInt()))
                .thenReturn(idGenerationResponse);
    }
}

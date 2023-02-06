package org.egov.works.validator;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.helper.MDMSTestBuilder;
import org.egov.works.helper.ProjectRequestTestBuilder;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.util.BoundaryUtil;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.ProjectRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class ProjectValidatorTest {

    @InjectMocks
    private ProjectValidator projectValidator;

    @Mock
    private MDMSUtils mdmsUtils;

    @Mock
    private BoundaryUtil boundaryUtil;


    @BeforeEach
    void setUp() throws Exception {
        //MOCK MDMS Response
        Object mdmsResponse = MDMSTestBuilder.builder().getMockMDMSData();
        lenient().when(mdmsUtils.mDMSCall(any(ProjectRequest.class),
                any(String.class))).thenReturn(mdmsResponse);

    }

    @Test
    void shouldNotThrowException_IfCreateValidationSuccess() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        assertDoesNotThrow(() -> projectValidator.validateCreateProjectRequest(projectRequest));
    }

    @Test
    void shouldThrowException_IfRequestInfoIsNullForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.setRequestInfo(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("REQUEST_INFO"));
    }

    @Test
    void shouldThrowException_IfUserInfoIsNullForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getRequestInfo().setUserInfo(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("USERINFO"));
    }

    @Test
    void shouldThrowException_IfUUIDIsBlankForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getRequestInfo().getUserInfo().setUuid(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("USERINFO_UUID"));
    }

    @Test
    void shouldThrowException_IfProjectIsNullForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.setProjects(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("Projects are mandatory"));
    }

    @Test
    void shouldThrowException_IfTenantIdIsNullForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setTenantId(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("Tenant ID is mandatory"));
    }

    @Test
    void shouldThrowException_IfMultipleTenantsArePresentForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withMultipleProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setTenantId("t1");
        projectRequest.getProjects().get(1).setTenantId("t2");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("MULTIPLE_TENANTS"));
    }

    @Test
    void shouldThrowException_IfEndDataLessThanStartDateForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withMultipleProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setStartDate(new Long("1669919400000"));
        projectRequest.getProjects().get(0).setEndDate(new Long("1569919400000"));
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_DATE"));
    }


    @Test
    void shouldThrowException_IfProjectTypeInValidForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setProjectType("Type-1");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_PROJECT_TYPE"));
    }

    @Test
    void shouldThrowException_IfProjectSubTypeInValidForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setProjectSubType("Type-2");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_PROJECT_SUB_TYPE"));
    }

    @Test
    void shouldThrowException_IfTenantIdInValidForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setTenantId("t12");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_TENANT"));
    }

    @Test
    void shouldThrowException_IfDepartmentInValidForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setDepartment("D1");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_DEPARTMENT_CODE"));
    }

    @Test
    void shouldThrowException_IfBeneficiaryInValidForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).getTargets().get(0).setBeneficiaryType("B1");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_BENEFICIARY_TYPE"));
    }

    @Test
    void shouldThrowException_IfRequestInfoIsNullForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().addGoodProjectForSearch().build();
        projectRequest.setRequestInfo(null);
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId")));
        assertTrue(exception.toString().contains("REQUEST_INFO"));
    }

    @Test
    void shouldThrowException_IfUserInfoIsNullForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        projectRequest.getRequestInfo().setUserInfo(null);
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId")));
        assertTrue(exception.toString().contains("USERINFO"));
    }

    @Test
    void shouldThrowException_IfLimitNotProvidedForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                null, (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId")));
        assertTrue(exception.toString().contains("limit is mandatory"));
    }

    @Test
    void shouldThrowException_IfOffsetNotProvidedForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                 (Integer)searchParams.get("limit"), null,
                (String)searchParams.get("tenantId")));
        assertTrue(exception.toString().contains("offset is mandatory"));
    }

    @Test
    void shouldThrowException_IfTenantIdNotProvidedForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                null));
        assertTrue(exception.toString().contains("tenantId is mandatory"));
    }

    @Test
    void shouldThrowException_IfProjectIsNullForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        projectRequest.setProjects(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId")));
        assertTrue(exception.toString().contains("Projects are mandatory"));
    }

    @Test
    void shouldThrowException_IfAllProjectFieldsEmptyInSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addBadProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId")));
        System.out.println(exception.toString());
        assertTrue(exception.toString().contains("Any one project search field is required"));
    }

    @Test
    void shouldThrowException_IfTenantIdIsNullForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProject().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        projectRequest.getProjects().get(0).setTenantId(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId")));
        assertTrue(exception.toString().contains("Tenant ID is mandatory"));
    }

    @Test
    void shouldThrowException_IfMultipleTenantsArePresentForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProject().addGoodProject().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        projectRequest.getProjects().get(0).setTenantId("t1");
        projectRequest.getProjects().get(1).setTenantId("t2");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId")));
        assertTrue(exception.toString().contains("All projects must have same tenant Id"));
    }


}

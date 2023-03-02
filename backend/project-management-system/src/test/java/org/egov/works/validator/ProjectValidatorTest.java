package org.egov.works.validator;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ProjectConfiguration;
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

    @Mock
    private ProjectConfiguration config;


    @BeforeEach
    void setUp() throws Exception {
        //MOCK MDMS Response
        Object mdmsResponse = MDMSTestBuilder.builder().getMockMDMSData();
        lenient().when(mdmsUtils.mDMSCall(any(ProjectRequest.class),
                any(String.class))).thenReturn(mdmsResponse);
        lenient().when(config.getDocumentIdVerificationRequired()).thenReturn("false");
        lenient().when(config.getMdmsModule()).thenReturn("works");

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
        assertTrue(exception.toString().contains("Request info is mandatory"));
    }

    @Test
    void shouldThrowException_IfUserInfoIsNullForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getRequestInfo().setUserInfo(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("UserInfo is mandatory"));
    }

    @Test
    void shouldThrowException_IfUUIDIsBlankForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getRequestInfo().getUserInfo().setUuid(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("UUID is mandatory"));
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
        assertTrue(exception.toString().contains("All projects must have same tenant Id. Please create new request for different tentant id"));
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
    void shouldThrowException_IfNatureOfWorkInValidForCreateProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setNatureOfWork("Work2");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_NATURE_OF_WORK"));
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
    void shouldThrowException_IfRequestInfoIsNullForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().addGoodProjectForSearch().build();
        projectRequest.setRequestInfo(null);
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("Request info is mandatory"));
    }

    @Test
    void shouldThrowException_IfUserInfoIsNullForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        projectRequest.getRequestInfo().setUserInfo(null);
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("UserInfo is mandatory"));
    }

    @Test
    void shouldThrowException_IfLimitNotProvidedForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                null, (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("limit is mandatory"));
    }

    @Test
    void shouldThrowException_IfOffsetNotProvidedForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                 (Integer)searchParams.get("limit"), null,
                (String)searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("offset is mandatory"));
    }

    @Test
    void shouldThrowException_IfTenantIdNotProvidedForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                null, (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("tenantId is mandatory"));
    }

    @Test
    void shouldThrowException_IfRequestTenantIdMismathesWithParamTenantIdForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        projectRequest.getProjects().get(0).setTenantId("t9");
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String) searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("Tenant Id must be same in URL param and project request"));
    }

    @Test
    void shouldThrowException_IfEndDateIsProvidedAndStartDateNotProvidedForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        projectRequest.getProjects().get(0).setStartDate(null);
        projectRequest.getProjects().get(0).setEndDate(1L);
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String) searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("Start date is required if end date is passed"));
    }

    @Test
    void shouldThrowException_IfEndDateLessThanStartDateForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        projectRequest.getProjects().get(0).setStartDate(10000L);
        projectRequest.getProjects().get(0).setEndDate(5000L);
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String) searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("Start date should be less than end date"));
    }

    @Test
    void shouldThrowException_IfCreatedFromGreaterThankCreatedToForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String) searchParams.get("tenantId"), 10000L, 500L));
        assertTrue(exception.toString().contains("Created From should be less than Created To"));
    }

    @Test
    void shouldThrowException_IfCreatedFromDateMissingWhenCreatedToGivenForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String) searchParams.get("tenantId"), null, 500L));
        assertTrue(exception.toString().contains("Created From date is required if Created To date is given"));
    }

    @Test
    void shouldThrowException_IfProjectIsNullForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        projectRequest.setProjects(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("Projects are mandatory"));
    }

    @Test
    void shouldThrowException_IfAllProjectFieldsEmptyInSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addBadProjectForSearch().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("Any one project search field is required"));
    }

    @Test
    void shouldThrowException_IfTenantIdIsNullForSearchProject() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withRequestInfo().addGoodProject().build();
        Map<String, Object> searchParams = ProjectRequestTestBuilder.builder().getSearchProjectParams();
        projectRequest.getProjects().get(0).setTenantId(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateSearchProjectRequest(projectRequest,
                (Integer)searchParams.get("limit"), (Integer)searchParams.get("offset"),
                (String)searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
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
                (String)searchParams.get("tenantId"), (Long)searchParams.get("createdFrom"), (Long)searchParams.get("createdTo")));
        assertTrue(exception.toString().contains("Tenant Id must be same in URL param and project request"));
    }


}

package org.egov.works.validator;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.helper.MDMSTestBuilder;
import org.egov.works.helper.ProjectRequestTestBuilder;
import org.egov.works.util.LocationUtil;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.ProjectRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private LocationUtil locationUtil;

    @BeforeEach
    void setUp() throws Exception {
        //MOCK MDMS Response
        Object mdmsResponse = MDMSTestBuilder.builder().getMockMDMSData();
        Object locationMdmsResponse = MDMSTestBuilder.builder().getMockLocationMDMSData();
        lenient().when(mdmsUtils.mDMSCall(any(ProjectRequest.class),
                any(String.class))).thenReturn(mdmsResponse);
        lenient().when(locationUtil.getLocationFromMDMS(any(ArrayList.class),
                any(String.class), any(RequestInfo.class))).thenReturn(locationMdmsResponse);
    }

    @Test
    void shouldNotThrowException_IfCreateValidationSuccess() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        assertDoesNotThrow(() -> projectValidator.validateCreateProjectRequest(projectRequest));
    }

    @Test
    void shouldThrowException_IfRequestInfoIsNull() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.setRequestInfo(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("REQUEST_INFO"));
    }

    @Test
    void shouldThrowException_IfUserInfoIsNull() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getRequestInfo().setUserInfo(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("USERINFO"));
    }

    @Test
    void shouldThrowException_IfUUIDIsBlank() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getRequestInfo().getUserInfo().setUuid(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("USERINFO_UUID"));
    }

    @Test
    void shouldThrowException_IfProjectIsNull() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.setProjects(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("Projects are mandatory"));
    }

    @Test
    void shouldThrowException_IfTenantIdIsNull() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setTenantId(null);
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("Tenant ID is mandatory"));
    }

    @Test
    void shouldThrowException_IfMultipleTenantsArePresent() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withMultipleProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setTenantId("pb");
        projectRequest.getProjects().get(1).setTenantId("od");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("MULTIPLE_TENANTS"));
    }

    @Test
    void shouldThrowException_IfEndDataLessThanStartDate() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withMultipleProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setStartDate(new Long("1669919400000"));
        projectRequest.getProjects().get(0).setEndDate(new Long("1569919400000"));
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_DATE"));
    }


    @Test
    void shouldThrowException_IfProjectTypeInValid() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setProjectType("Type-1");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_PROJECT_TYPE"));
    }

    @Test
    void shouldThrowException_IfProjectSubTypeInValid() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setProjectSubType("Type-2");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_PROJECT_SUB_TYPE"));
    }

    @Test
    void shouldThrowException_IfTenantIdInValid() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setTenantId("T1");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_TENANT"));
    }

    @Test
    void shouldThrowException_IfDepartmentInValid() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).setDepartment("D1");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_DEPARTMENT_CODE"));
    }

    @Test
    void shouldThrowException_IfBeneficiaryInValid() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).getTargets().get(0).setBeneficiaryType("B1");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("INVALID_BENEFICIARY_TYPE"));
    }

    @Test
    void shouldThrowException_IfInValidLocationMDMSData() {
        ProjectRequest projectRequest = ProjectRequestTestBuilder.builder().withProjectForCreateValidationSuccess().build();
        projectRequest.getProjects().get(0).getAddress().setLocality("SUN99");
        CustomException exception = assertThrows(CustomException.class, ()-> projectValidator.validateCreateProjectRequest(projectRequest));
        assertTrue(exception.toString().contains("LOCATION_NOT_FOUND"));
    }
}

package org.egov.validator;

import lombok.extern.slf4j.Slf4j;
import org.egov.helper.EstimateRequestBuilderTest;
import org.egov.repository.EstimateRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.MDMSUtils;
import org.egov.util.ProjectUtil;
import org.egov.web.models.EstimateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@Slf4j
class EstimateServiceValidatorTest {

    @InjectMocks
    private EstimateServiceValidator serviceValidator;

    @Mock
    private MDMSUtils mdmsUtils;

    @Mock
    private EstimateRepository estimateRepository;

    @Mock
    private ProjectUtil projectUtil;

    @BeforeEach
    void setUp() throws Exception {
        Object mdmsResponse = EstimateRequestBuilderTest.getMdmsResponse();
        lenient().when(mdmsUtils.mDMSCall(any(EstimateRequest.class),any(String.class))).thenReturn(mdmsResponse);
        lenient().when(mdmsUtils.mDMSCallForOverHeadCategory(any(EstimateRequest.class),any(String.class))).thenReturn(mdmsResponse);

        Object projectResponse = EstimateRequestBuilderTest.getProjectSearchResponse();
        lenient().when(projectUtil.getProjectDetails(any(EstimateRequest.class))).thenReturn(projectResponse);
    }


    @Test
    void validateEstimateOnCreate_IfEstimateIsNull() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.setEstimate(null);
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("Estimate is mandatory"));
    }

    @Test
    void validateEstimateOnCreate_IfRequestInfoIsNull() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.setRequestInfo(null);
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("Request info is mandatory"));
    }

    @Test
    void validateEstimateOnCreate_IfUserInfoIsNull() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.getRequestInfo().setUserInfo(null);
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("UserInfo is mandatory"));
    }

    @Test
    void validateEstimateOnCreate_IfUserUUIDIsNull() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.getRequestInfo().getUserInfo().setUuid(null);
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("UUID is mandatory"));
    }

    @Test
    void validateEstimateOnCreate_IfStatusIsNull() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.getEstimate().setStatus(null);
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("{STATUS=Status is mandatory}"));
    }

    @Test
    void validateEstimateOnCreate_IfDepartmentIsNull() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.getEstimate().setExecutingDepartment(null);
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("{EXECUTING_DEPARTMENT=Executing department is mandatory}"));
    }

    @Test
    void validateEstimateOnCreate_IfDepartmentIsEMpty() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.getEstimate().setExecutingDepartment(" ");
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("{EXECUTING_DEPARTMENT=Executing department is mandatory}"));
    }

    @Test
    void validateEstimateOnCreate_IfProjectIsEmpty() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.getEstimate().setProjectId(" ");
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("{PROJECT_ID=ProjectId is mandatory}"));
    }

    @Test
    void validateEstimateOnCreate_IfAddressIsNull() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.getEstimate().setAddress(null);
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("{ADDRESS=Address is mandatory}"));
    }

    @Test
    void validateEstimateOnCreate_IfEstimateDetailIsNull() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.getEstimate().setEstimateDetails(null);
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("{ESTIMATE_DETAILS=Please ensure that the estimate has a SOR or Non SOR item added.}"));
    }

    @Test
    void validateEstimateOnCreate_IfWorkflowIsNull() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.setWorkflow(null);
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("Work flow is mandatory"));
    }

    @Test
    void validateEstimateOnCreate_IfWorkflowActionIsNull() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.getWorkflow().setAction(null);
        CustomException exception = assertThrows(CustomException.class, ()-> serviceValidator.validateEstimateOnCreate(estimateRequest));
        assertTrue(exception.getMessage().contentEquals("Work flow's action is mandatory"));
    }

}
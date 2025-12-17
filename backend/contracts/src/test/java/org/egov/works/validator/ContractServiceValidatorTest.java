package org.egov.works.validator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.JSONParser;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.helper.ContractRequestTestBuilder;
import org.egov.works.helper.ContractTestBuilder;
import org.egov.works.repository.ContractRepository;
import org.egov.works.repository.LineItemsRepository;
import org.egov.works.service.ContractService;
import org.egov.works.services.common.models.estimate.AmountDetail;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateDetail;
import org.egov.works.util.CommonUtil;
import org.egov.works.util.EstimateServiceUtil;
import org.egov.works.util.HRMSUtils;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@Slf4j
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class ContractServiceValidatorTest {
    @InjectMocks
    private ContractServiceValidator contractServiceValidator;
    @Mock
    private MDMSUtils mdmsUtil;
    @Mock
    private CommonUtil commonUtil;
    @Mock
    private HRMSUtils hrmsUtils;
    @Mock
    private ContractRepository contractRepository;
    @Mock
    private EstimateServiceUtil estimateServiceUtil;
    @Mock
    private ContractService contractService;

    @BeforeEach
    public void setUpBeforeEach() throws IOException {
        List<Object> someContractInfo =new ArrayList<>();
        someContractInfo.add("pb.amritsar");
        someContractInfo.add("some-executingAuthority");
        someContractInfo.add("some-contractType");
        someContractInfo.add("some-roles");
        when(commonUtil.readJSONPathValue(any(Object.class),any(String.class))).thenReturn(someContractInfo);
        when(commonUtil.findValue(any(Object.class),any(String.class))).thenReturn(Optional.of("field-present"));
        Object mdmsResponse = ContractRequestTestBuilder.getMdmsResponseForValidTenant();
        lenient().when(mdmsUtil.fetchMDMSForValidation(any(RequestInfo.class), any(String.class))).thenReturn(mdmsResponse);
    }

    @DisplayName("Method  validateCreateContractRequest: With good request")
    @Test
    public void validateCreateContractRequest_validateRequestInfo_1() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().withWorkflow().build();
        assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequestInfo", contractRequest.getRequestInfo()));

    }

    @DisplayName("Method validateCreateContractRequest:With null request info object")
    @Test
    public void validateCreateContractRequest_validateRequestInfo_2() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().build();
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequestInfo", contractRequest.getRequestInfo()));
        assertTrue(exception.getCode().equals("REQUEST_INFO"));

    }

    @DisplayName("method validatorCreateContractRequest: RequestInfo object with null User info")
    @Test
    public void validateCreateContractRequest_validateRequestInfo_3() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfoButWithoutUserInfo().withContract().withWorkflow().build();
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequestInfo", contractRequest.getRequestInfo()));
        assertTrue(exception.getCode().equals("USERINFO"));
    }

    @DisplayName("method validatorCreateRequest:RequestInfo object with UserInfo ut without UUID")
    @Test
    public void validateCreateContractRequest_validateRequestInfo_4() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfoWithUserInfoButWithoutUUID().build();
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequestInfo", contractRequest.getRequestInfo()));
        assertTrue(exception.getCode().equals("USERINFO_UUID"));

    }

    @DisplayName("method validatorCreateRequest:RequestInfo object with UserInfo ut without UUID")
    @Test
    public void validateCreateContractRequest_validateRequestInfo_5() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfoWithUserInfoButWithoutUUID().build();
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequestInfo", contractRequest.getRequestInfo()));
        assertTrue(exception.getCode().equals("USERINFO_UUID"));

    }

    @DisplayName("method validatorCreateRequest:With valid contract")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_1() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
    }

    @DisplayName("method validatorCreateRequest:With Contract object but null contract")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_2() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().build();
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
        assertTrue(exception.toString().contains("Contract is mandatory"));
    }

    @DisplayName("method validatorCreateRequest:With Contract object but without TenantId")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_3() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().setTenantId(null);
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
        assertTrue(exception.toString().contains("TenantId is mandatory"));
    }

    @DisplayName("method validatorCreateRequest:With Contract object but without executing authority")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_4() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().setExecutingAuthority(null);
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
        assertTrue(exception.toString().contains("Executing Authority is mandatory"));
    }

    @DisplayName("method validatorCreateRequest:With Contract object but without contract")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_5() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().setContractType(null);
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
        assertTrue(exception.toString().contains("Contract Type is mandatory"));
    }

    @DisplayName("method validatorCreateRequest:With Contract object but without OrganisationId")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_6() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().setOrgId(null);
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
        assertTrue(exception.toString().contains("OrgnisationId is mandatory"));
    }

    @DisplayName("method validatorCreateRequest:With Contract object but without OrganisationId")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_7() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        List<LineItems> lineItems = new ArrayList<>();
        contractRequest.getContract().setLineItems(lineItems);
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
        assertTrue(exception.toString().contains("LineItem is mandatory"));
    }

    @DisplayName("method validatorCreateRequest:With Contract object but without LineItems.OrganisationId")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_8() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().getLineItems().get(0).setEstimateId("");
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
        assertTrue(exception.toString().contains("LineItems.EstimateId is mandatory"));
    }

    @DisplayName("method validatorCreateRequest:With Contract object but without LineItems.TenantId ")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_9() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().getLineItems().get(0).setTenantId("");
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
        assertTrue(exception.toString().contains("LineItems.TenantId is mandatory"));
    }

    @DisplayName("method validatorCreateRequest:With Contract object but without Estimate Amount BreakupId ")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_10() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().getLineItems().get(0).getAmountBreakups().get(0).setEstimateAmountBreakupId("");
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
        assertTrue(exception.toString().contains("Estimate Amount BreakupId is mandatory"));
    }

    @DisplayName("method validatorCreateRequest:With Contract object but without Estimate Amount BreakupId ")
    @Test
    public void validateCreateContractRequest_validateRequiredParameters_11() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().getLineItems().get(0).getAmountBreakups().get(0).setAmount(null);
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequiredParameters", contractRequest));
        assertTrue(exception.toString().contains("Breakup Amount is mandatory"));
    }

    @DisplayName("method validatorCreateRequest:With same tenantId inside Contract object")
    @Test
    public void validateCreateContractRequest_validateMultipleTenantIds_1() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateMultipleTenantIds", contractRequest));
    }

    @DisplayName("method validatorCreateRequest:With different tenantId inside Contract object")
    @Test
    public void validateCreateContractRequest_validateMultipleTenantIds_2() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().getLineItems().get(0).setTenantId("No-same");
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateMultipleTenantIds", contractRequest));
        assertTrue(exception.toString().contains("Contract and corresponding lineItems should belong to same tenantId"));

    }

//    @DisplayName("method validatorCreateRequest:With contract object but starting date is smaller then agreement date")
//    @Test
//    public void validateCreateContractRequest_validateContractDates_1() {
//        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
//        contractRequest.getContract().setStartDate(BigDecimal.valueOf(12022000));
//        contractRequest.getContract().setAgreementDate(BigDecimal.valueOf(12022022));
//        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateContractDates", contractRequest));
//        assertTrue(exception.toString().contains("Invalid contract start date"));
//
//    }
//
//    @DisplayName("method validatorCreateRequest:With contract object but starting date is grater than ending date")
//    @Test
//    public void validateCreateContractRequest_validateContractDates_2() {
//        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
//        contractRequest.getContract().setStartDate(BigDecimal.valueOf(12022023));
//        contractRequest.getContract().setEndDate(BigDecimal.valueOf(12022022));
//        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateContractDates", contractRequest));
//        assertTrue(exception.toString().contains("Invalid contract end date"));
//
//    }
//
//    @DisplayName("method validatorCreateRequest:With valid contract object ")
//    @Test
//    public void validateCreateContractRequest_validateContractDates_3() {
//        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
//        assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateContractDates", contractRequest));
//
//    }

    @DisplayName("method validatorContractContractRequest: With valid request failed against MDMS data")
    @Test
    public void validateCreateContractRequest_validateRequestFieldsAgainstMDMS_1() throws IOException {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        when(hrmsUtils.getRoleCodesByEmployeeId(any(RequestInfo.class),any(String.class),anyList())).thenReturn(Collections.singletonList("some-roles"));
        assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequestFieldsAgainstMDMS", contractRequest));

    }

    @DisplayName("method validatorContractContractRequest: Will give exception when tenant id is different against MDMS data")
    @Test
    public void validateCreateContractRequest_validateRequestFieldsAgainstMDMS_2() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().setTenantId("different-tenantId");

        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequestFieldsAgainstMDMS", contractRequest));
        assertTrue(exception.getCode().toString().equals("INVALID_TENANT"));
    }

    @DisplayName("method validatorContractContractRequest: Will give exception when executing authority mismatch against MDMS data")
    @Test
    public void validateCreateContractRequest_validateRequestFieldsAgainstMDMS_3() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().setExecutingAuthority("some-differentType");
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequestFieldsAgainstMDMS", contractRequest));
        assertTrue(exception.getCode().toString().equals("INVALID_EXECUTING_AUTHORITY"));
    }

    @DisplayName("method validatorContractContractRequest: Will give exception when contract type mismatch against MDMS data")
    @Test
    public void validateCreateContractRequest_validateRequestFieldsAgainstMDMS_4() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().setContractType("some-differentType");
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequestFieldsAgainstMDMS", contractRequest));
        assertTrue(exception.getCode().toString().equals("INVALID_CONTRACT_TYPE"));
    }

    @DisplayName("method validatorContractContractRequest: Will give exception when executingAuthority mismatch against MDMS data")
    @Test
    public void validateCreateContractRequest_validateRequestFieldsAgainstMDMS_5() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        contractRequest.getContract().setExecutingAuthority("some-differentType");
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateRequestFieldsAgainstMDMS", contractRequest));
        assertTrue(exception.getCode().toString().equals("INVALID_EXECUTING_AUTHORITY"));
    }

    @DisplayName("method validatorCreateRequest:With valid Estimate id against estimate service ")
    @Test
    public void validateCreateContractRequest_validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB_1() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        EstimateDetail estimateDetail = EstimateDetail.builder().id("some-estimateId").amountDetail(Collections.singletonList(AmountDetail.builder().id("some-estimateId").build())).build();
        List<EstimateDetail> estimateDetails = new ArrayList<>();
        estimateDetails.add(estimateDetail);
        Estimate estimate = Estimate.builder().id("some-estimateId").estimateDetails(estimateDetails).build();
        List<Estimate> estimates = new ArrayList<>();
        estimates.add(estimate);
        lenient().when(estimateServiceUtil.fetchActiveEstimates(any(RequestInfo.class), any(String.class), anySet())).thenReturn(estimates);
//        assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB", contractRequest));

    }

    @DisplayName("method validatorCreateRequest:With different estimate id  ")
    @Test
    public void validateCreateContractRequest_validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB_2() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        EstimateDetail estimateDetail = EstimateDetail.builder().id("some-estimateId").amountDetail(Collections.singletonList(AmountDetail.builder().id("some-estimateId").build())).build();
        List<EstimateDetail> estimateDetails = new ArrayList<>();
        estimateDetails.add(estimateDetail);
        Estimate estimate = Estimate.builder().id("different-EstimateId").estimateDetails(estimateDetails).build();
        List<Estimate> estimates = new ArrayList<>();
        estimates.add(estimate);
        lenient().when(estimateServiceUtil.fetchActiveEstimates(any(RequestInfo.class), any(String.class), anySet())).thenReturn(estimates);
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB", contractRequest));
        assertTrue(exception.getCode().toString().equals("ESTIMATE_NOT_ACTIVE"));
    }

    @DisplayName("method validatorCreateRequest:With different EstimateAmountBreakupId ")
    @Test
    public void validateCreateContractRequest_validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB_3() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        EstimateDetail estimateDetail = EstimateDetail.builder().id("some-estimateId").amountDetail(Collections.singletonList(AmountDetail.builder().id("different-EstimateAmountBreakupId").build())).build();
        List<EstimateDetail> estimateDetails = new ArrayList<>();
        estimateDetails.add(estimateDetail);
        Estimate estimate = Estimate.builder().id("some-estimateId").estimateDetails(estimateDetails).build();
        List<Estimate> estimates = new ArrayList<>();
        estimates.add(estimate);
//        lenient().when(estimateServiceUtil.fetchEstimates(eq(contractRequest.getRequestInfo()),eq("pb.amritsar"), Collections.singleton(eq(contractRequest.getContract().getLineItems().get(0).getEstimateId())))).thenReturn(estimates);
        lenient().when(estimateServiceUtil.fetchActiveEstimates(any(RequestInfo.class), any(String.class), anySet())).thenReturn(estimates);
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB", contractRequest));
        assertTrue(exception.getCode().toString().equals("INVALID_ESTIMATEAMOUNTBREAKUPID"));
    }

    @DisplayName("method validatorCreateRequest:With different estimate lineItemId")
    @Test
    public void validateCreateContractRequest_validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB_4() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        EstimateDetail estimateDetail = EstimateDetail.builder().id("different-estimateId").amountDetail(Collections.singletonList(AmountDetail.builder().id("some-estimateAmountBreakupId").build())).build();
        List<EstimateDetail> estimateDetails = new ArrayList<>();
        estimateDetails.add(estimateDetail);
        Estimate estimate = Estimate.builder().id("some-estimateId").estimateDetails(estimateDetails).build();
        List<Estimate> estimates = new ArrayList<>();
        estimates.add(estimate);
        lenient().when(estimateServiceUtil.fetchActiveEstimates(any(RequestInfo.class), any(String.class), anySet())).thenReturn(estimates);
        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB", contractRequest));
        assertTrue(exception.getCode().toString().equals("INVALID_ESTIMATELINEITEMID"));
    }

//    @DisplayName("method validatorCreateRequest:should through exception with error code SERVICE_UNAVAILABLE ")
//    @Test
//    public void validateCreateContractRequest_validateOrganizationIdAgainstOrgService_1() {
//        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
//        when(config.getOrgIdVerificationRequired()).thenReturn("TRUE");
//        CustomException exception = assertThrows(CustomException.class, () -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateOrganizationIdAgainstOrgService", contractRequest));
//        assertTrue(exception.getCode().equals("SERVICE_UNAVAILABLE"));
//
//    }

//    @DisplayName("method validatorCreateRequest:should run successfully ")
//    @Test
//    public void validateCreateContractRequest_validateOrganizationIdAgainstOrgService_2() {
//        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
//        when(config.getOrgIdVerificationRequired()).thenReturn("FALSE");
//        assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateOrganizationIdAgainstOrgService", contractRequest));
//
//    }


    @DisplayName("method  validateUpdateContractRequest:should run successfully ")
    @Test
    public void  validateUpdateContractRequest_validateContractAgainstDB_1() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        ContractCriteria contractCriteria = ContractCriteria.builder().ids(Collections.singletonList(contractRequest.getContract().getId())).tenantId(contractRequest.getContract().getTenantId()).build();
        List<Contract> fetchedContracts=new ArrayList<>();
        fetchedContracts.add(ContractTestBuilder.builder().withValidContract().build());
        when(contractService.searchContracts(any(ContractCriteria.class))).thenReturn(fetchedContracts);
        when(contractRepository.getContracts(any(ContractCriteria.class))).thenReturn(Collections.singletonList((contractRequest.getContract())));
//        assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateContractAgainstDB", contractRequest));

    }
    @DisplayName("method  validateUpdateContractRequest:Contract id is mandatory ")
    @Test
    public void  validateUpdateContractRequest_validateContractAgainstDB_2() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        List<Contract> fetchedContracts=new ArrayList<>();
        when(contractService.searchContracts(any(ContractCriteria.class))).thenReturn(fetchedContracts);
        when(contractRepository.getContracts(any(ContractCriteria.class))).thenReturn(fetchedContracts);
//        CustomException exception = assertThrows(CustomException.class,() -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateContractAgainstDB", contractRequest));
//        assertTrue(exception.getCode().equals("CONTRACT_NOT_FOUND"));
    }

    @DisplayName("method  validateUpdateContractRequest:should run successfully ")
    @Test
    public void  validateUpdateContractRequest_validateUpdateRequestedEstimateIdsAgainstEstimateServiceAndDB_1() {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().build();
        EstimateDetail estimateDetail = EstimateDetail.builder().id("some-estimateId").amountDetail(Collections.singletonList(AmountDetail.builder().id("some-estimateId").build())).build();
        List<EstimateDetail> estimateDetails = new ArrayList<>();
        estimateDetails.add(estimateDetail);
        Estimate estimate = Estimate.builder().id("some-estimateId").estimateDetails(estimateDetails).build();
        List<Estimate> estimates = new ArrayList<>();
        estimates.add(estimate);
        lenient().when(estimateServiceUtil.fetchActiveEstimates(any(RequestInfo.class), any(String.class), anySet())).thenReturn(estimates);
//        assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(contractServiceValidator, "validateUpdateRequestedEstimateIdsAgainstEstimateServiceAndDB", contractRequest));

    }



}

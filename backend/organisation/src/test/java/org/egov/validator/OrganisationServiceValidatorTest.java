package org.egov.validator;

import digit.models.coremodels.mdms.MasterDetail;
import digit.models.coremodels.mdms.MdmsCriteria;
import digit.models.coremodels.mdms.MdmsCriteriaReq;
import digit.models.coremodels.mdms.ModuleDetail;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.helper.MDMSTestBuilder;
import org.egov.helper.OrganisationRequestTestBuilder;
import org.egov.helper.OrganisationTestBuilder;
import org.egov.repository.OrganisationRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.BoundaryUtil;
import org.egov.util.MDMSUtil;
import org.egov.web.models.OrgRequest;
import org.egov.web.models.Organisation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class OrganisationServiceValidatorTest {

    @InjectMocks
    private OrganisationServiceValidator organisationServiceValidator;

    @Mock
    private MDMSUtil mdmsUtil;

    @Mock
    private OrganisationRepository organisationRepository;

    @Mock
    private BoundaryUtil boundaryUtil;

    @Mock
    private Configuration config;

    @BeforeEach
    void setUp() throws Exception {
        //MOCK MDMS Response
        Object tenantMDMSResponse = MDMSTestBuilder.builder().getMockTenantMDMSData();
        Object orgTypeMDMSResponse = MDMSTestBuilder.builder().getMockOrganisationTypeMDMSData();
        Object funcCategoryMDMSResponse = MDMSTestBuilder.builder().getMockFunctionCategoryMDMSData();
        Object funClassMDMSResponse = MDMSTestBuilder.builder().getMockFunctionClassMDMSData();
        Object taxIdentifierMDMSResponse = MDMSTestBuilder.builder().getMockTaxIdentifierMDMSData();

        MdmsCriteriaReq mdmsCriteriaReqForTenant = MdmsCriteriaReq.builder()
                .mdmsCriteria(MdmsCriteria.builder()
                        .moduleDetails(Collections.singletonList(ModuleDetail.builder()
                                .masterDetails(Collections.singletonList(MasterDetail.builder()
                                        .name("tenant")
                                        .build()))
                                .build()))
                        .build())
                .build();
        MdmsCriteriaReq mdmsCriteriaReqForOrgType = MdmsCriteriaReq.builder()
                .mdmsCriteria(MdmsCriteria.builder()
                        .moduleDetails(Collections.singletonList(ModuleDetail.builder()
                                .masterDetails(Collections.singletonList(MasterDetail.builder()
                                        .name("OrgType")
                                        .build()))
                                .build()))
                        .build())
                .build();
        MdmsCriteriaReq mdmsCriteriaReqForFuncCategory = MdmsCriteriaReq.builder()
                .mdmsCriteria(MdmsCriteria.builder()
                        .moduleDetails(Collections.singletonList(ModuleDetail.builder()
                                .masterDetails(Collections.singletonList(MasterDetail.builder()
                                        .name("OrgFunctionCategory")
                                        .build()))
                                .build()))
                        .build())
                .build();
        MdmsCriteriaReq mdmsCriteriaReqForFunClass = MdmsCriteriaReq.builder()
                .mdmsCriteria(MdmsCriteria.builder()
                        .moduleDetails(Collections.singletonList(ModuleDetail.builder()
                                .masterDetails(Collections.singletonList(MasterDetail.builder()
                                        .name("OrgFunctionClass")
                                        .build()))
                                .build()))
                        .build())
                .build();
        MdmsCriteriaReq mdmsCriteriaReqForTaxIdentifier = MdmsCriteriaReq.builder()
                .mdmsCriteria(MdmsCriteria.builder()
                        .moduleDetails(Collections.singletonList(ModuleDetail.builder()
                                .masterDetails(Collections.singletonList(MasterDetail.builder()
                                        .name("OrgTaxIdentifier")
                                        .build()))
                                .build()))
                        .build())
                .build();
        lenient().when(mdmsUtil.getTenantMDMSRequest(any(RequestInfo.class),
                any(String.class), any(List.class))).thenReturn(mdmsCriteriaReqForTenant);
        lenient().when(mdmsUtil.mDMSCall(eq(mdmsCriteriaReqForTenant),
                any(String.class))).thenReturn(tenantMDMSResponse);

        lenient().when(mdmsUtil.getOrgTypeMDMSRequest(any(RequestInfo.class),
                any(String.class), any(List.class))).thenReturn(mdmsCriteriaReqForOrgType);
        lenient().when(mdmsUtil.mDMSCall(eq(mdmsCriteriaReqForOrgType),
                any(String.class))).thenReturn(orgTypeMDMSResponse);

        lenient().when(mdmsUtil.getOrgFunCategoryMDMSRequest(any(RequestInfo.class),
                any(String.class), any(List.class))).thenReturn(mdmsCriteriaReqForFuncCategory);
        lenient().when(mdmsUtil.mDMSCall(eq(mdmsCriteriaReqForFuncCategory),
                any(String.class))).thenReturn(funcCategoryMDMSResponse);

        lenient().when(mdmsUtil.getOrgTaxIdentifierMDMSRequest(any(RequestInfo.class),
                any(String.class), any(List.class))).thenReturn(mdmsCriteriaReqForTaxIdentifier);
        lenient().when(mdmsUtil.mDMSCall(eq(mdmsCriteriaReqForTaxIdentifier),
                any(String.class))).thenReturn(taxIdentifierMDMSResponse);

        lenient().when(mdmsUtil.getOrgFunctionMDMSRequest(any(RequestInfo.class),
                any(String.class), any(List.class))).thenReturn(mdmsCriteriaReqForFunClass);
        lenient().when(mdmsUtil.mDMSCall(eq(mdmsCriteriaReqForFunClass),
                any(String.class))).thenReturn(funClassMDMSResponse);

    }

    @Test
    void shouldNotThrowException_IfCreateValidationSuccess() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        assertDoesNotThrow(() -> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
    }

    @Test
    void shouldThrowException_IfRequestInfoIsNullForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().addGoodOrganisationForCreate().build();
        orgRequest.setRequestInfo(null);
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("Request info is mandatory"));
    }

    @Test
    void shouldThrowException_IfUserInfoIsNullForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getRequestInfo().setUserInfo(null);
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("UserInfo is mandatory"));
    }

    @Test
    void shouldThrowException_IfUUIDIsBlankForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getRequestInfo().getUserInfo().setUuid(null);
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("UUID is mandatory"));
    }

    @Test
    void shouldThrowException_IfProjectIsNullForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.setOrganisations(null);
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("At least one organisation detail is required"));
    }

    @Test
    void shouldThrowException_IfTenantIdIsNullForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getOrganisations().get(0).setTenantId(null);
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("Tenant id is mandatory"));
    }

    @Test
    void shouldThrowException_IfOrganisationNameIsNullForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getOrganisations().get(0).setName(null);
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        System.out.println(exception);
        assertTrue(exception.toString().contains("Organisation name is mandatory"));
    }

    @Test
    void shouldThrowException_IfAddressTenantIdIsNullForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getOrganisations().get(0).getOrgAddress().get(0).setTenantId(null);
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("Tenant id is mandatory"));
    }

    @Test
    void shouldThrowException_IfBoundaryTypeIsNullForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getOrganisations().get(0).getOrgAddress().get(0).setBoundaryType(null);
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("Address's boundary type is mandatory"));
    }

    @Test
    void shouldThrowException_IfBoundaryCodeIsNullForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getOrganisations().get(0).getOrgAddress().get(0).setBoundaryCode(null);
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("Address's boundary code is mandatory"));
    }

    @Test
    void shouldThrowException_IfOrgTypeInValidForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getOrganisations().get(0).getFunctions().get(0).setType("ot1");
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("INVALID_ORG_TYPE"));
    }

    @Test
    void shouldThrowException_IfFunctionCategoryInValidForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getOrganisations().get(0).getFunctions().get(0).setCategory("ot1");
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("INVALID_ORG.FUNCTION_CATEGORY"));
    }

    @Test
    void shouldThrowException_IfFunctionClassInValidForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getOrganisations().get(0).getFunctions().get(0).setPropertyClass("ot1");
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("INVALID_ORG.FUNCTION_CLASS"));
    }

    @Test
    void shouldThrowException_IfTaxIdentifierInValidForCreateOrganisation() {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        orgRequest.getOrganisations().get(0).getIdentifiers().get(0).setType("ot1");
        CustomException exception = assertThrows(CustomException.class, ()-> organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest));
        assertTrue(exception.toString().contains("INVALID_ORG.IDENTIFIER_TYPE"));
    }

}

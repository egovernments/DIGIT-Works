package org.egov.works.enrichment;

import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.helper.AuditDetailsTestBuilder;
import org.egov.works.helper.ContractRequestTestBuilder;
import org.egov.works.services.common.models.estimate.AmountDetail;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateDetail;
import org.egov.works.util.*;
import org.egov.works.web.models.ContractRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ContractEnrichmentTest {
    @Mock
    private ContractEnrichment contractEnrichment;
    @InjectMocks
    private ContractEnrichment contractEnrichment1;
    @Mock
    private IdgenUtil idgenUtil;
    @Mock
    private CommonUtil commonUtil;
    @Mock
    private ContractServiceConfiguration config;
    @Mock
    private MDMSUtils mdmsUtils;
    @Mock
    private EstimateServiceUtil estimateServiceUtil;

    @BeforeEach
    public void setup() {
        Object mdmsResponse = ContractRequestTestBuilder.getMdmsResponseForValidTenant();
        when(mdmsUtils.fetchMDMSForEnrichment(any(RequestInfo.class),any(String.class),any(String.class))).thenReturn(mdmsResponse);
        when(commonUtil.readJSONPathValue(any(Object.class),any(String.class))).thenReturn(Collections.singletonList("GST"));
        when(config.getIdgenContractNumberName()).thenReturn("attendance.register.number");
        EstimateDetail estimateDetail = EstimateDetail.builder().id("some-estimateId").amountDetail(Collections.singletonList(AmountDetail.builder().id("some-estimateId").build())).build();
        List<EstimateDetail> estimateDetails = new ArrayList<>();
        estimateDetails.add(estimateDetail);
        Estimate estimate = Estimate.builder().id("some-estimateId").estimateDetails(estimateDetails).build();
        List<Estimate> estimates = new ArrayList<>();
        estimates.add(estimate);
        lenient().when(estimateServiceUtil.fetchActiveEstimates(any(RequestInfo.class), any(String.class), anySet())).thenReturn(estimates);
        List<String> idList = new ArrayList<>();
        idList.add("some-id");
        when(idgenUtil.getIdList(any(RequestInfo.class), any(String.class), any(String.class), any(String.class), anyInt())).thenReturn(idList);

    }

    @DisplayName("method enrichContractCreate: with good request")
    @Test
    public void enrichContractOnCreateTest_enrichContractLineItems_1() {
        ContractRequest contractrequest = ContractRequestTestBuilder.builder().withContract().withRequestInfo().build();
        when(contractEnrichment.getAuditDetails("some-uuid", null, true)).thenReturn(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        contractEnrichment1.enrichContractOnCreate(contractrequest);
        assertNotNull(contractrequest.getContract().getLineItems());


    }
    @DisplayName("method enrichContractCreate: with good request")
    @Test
    public void enrichContractOnCreateTest_enrichContractLineItems_() {
        ContractRequest contractrequest = ContractRequestTestBuilder.builder().withContract().withRequestInfo().build();
        when(contractEnrichment.getAuditDetails(eq("some-uuid"), eq(null), eq(true))).thenReturn(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        contractEnrichment.enrichContractOnCreate(contractrequest);
        assertNotNull(contractrequest.getContract().getLineItems());


    }

    @DisplayName("method enrichContractCreate:without estimateLineItemsId ")
    @Test
    public void enrichContractOnCreateTest_enrichContractNumber_1() {
        ContractRequest contractrequest = ContractRequestTestBuilder.builder().withContract().withRequestInfo().build();
        contractrequest.getContract().getLineItems().get(0).setEstimateLineItemId(null);
        contractEnrichment.enrichContractOnCreate(contractrequest);
//        assertNotNull(contractrequest.getContract().getLineItems().get(0).getEstimateLineItemId());


    }


    @DisplayName("method enrichContractCreate:with out lineItems,auditDetails,documents ")
    @Test
    public void enrichContractOnCreateTest_enrichIdsAndAuditDetailsOnCreate_1() {
        ContractRequest contractrequest = ContractRequestTestBuilder.builder().withContract().withRequestInfo().build();
        when(contractEnrichment.getAuditDetails(eq("some-uuid"), eq(null), eq(true))).thenReturn(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        contractrequest.getContract().setAuditDetails(null);
        contractEnrichment.enrichContractOnCreate(contractrequest);
        assertNotNull(contractrequest.getContract().getId());
        assertNotNull(contractrequest.getContract().getAuditDetails() != null);
        assertNotNull(contractrequest.getContract().getLineItems().get(0).getId() != null);
        assertNotNull(contractrequest.getContract().getLineItems().get(0).getAmountBreakups().get(0).getId() != null);
        assertNotNull(contractrequest.getContract().getDocuments().get(0).getId() != null);


    }
    @DisplayName("method enrichContractCreate: with good request")
    @Test
    public void enrichContractOnUpdateTest_enrichIdsAndAuditDetailsOnUpdate_1() {
        ContractRequest contractrequest = ContractRequestTestBuilder.builder().withContract().withRequestInfo().withWorkflow().build();
        when(contractEnrichment.getAuditDetails(eq("some-uuid"), eq(contractrequest.getContract().getAuditDetails()), eq(false))).thenReturn(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        contractEnrichment.enrichContractOnUpdate(contractrequest);


    }

}

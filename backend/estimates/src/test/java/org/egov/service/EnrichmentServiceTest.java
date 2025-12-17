package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.idgen.IdGenerationResponse;
import org.egov.common.contract.idgen.IdResponse;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.helper.EstimateRequestBuilderTest;
import org.egov.repository.EstimateRepository;
import org.egov.repository.IdGenRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.EstimateServiceUtil;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class EnrichmentServiceTest {

    @InjectMocks
    private EnrichmentService enrichmentService;

    @Mock
    private EstimateServiceUtil estimateServiceUtil;

    @Mock
    private IdGenRepository idGenRepository;

    @Mock
    private EstimateServiceConfiguration config;

    @Mock
    private EstimateRepository estimateRepository;

    private IdGenerationResponse idGenerationResponse;

    @BeforeEach
    void setUp() throws Exception {
        IdResponse idResponse = IdResponse.builder().id("EP/2022-23/01/000260").build();
        List<IdResponse> idResponses = new ArrayList<>();
        idResponses.add(idResponse);
        idGenerationResponse = IdGenerationResponse.builder().idResponses(idResponses).build();
    }


    @Test
    void shouldEnrichAuditDetail_EstimateOnCreate() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        RequestInfo requestInfo = estimateRequest.getRequestInfo();
        Estimate estimate = estimateRequest.getEstimate();
        Long time = System.currentTimeMillis();
        AuditDetails auditDetails = AuditDetails.builder()
                .createdBy("local-test")
                .createdTime(time)
                .lastModifiedTime(time)
                .lastModifiedBy("local-test")
                .build();

        lenient().when(idGenRepository.getId(eq(requestInfo), eq("pb"), eq(null), eq(null), eq(1)))
                .thenReturn(idGenerationResponse);

        lenient().when(estimateServiceUtil.getAuditDetails(any(String.class), eq(estimate), eq(Boolean.TRUE)))
                .thenReturn(auditDetails);
        enrichmentService.enrichEstimateOnCreate(estimateRequest);
        assertNotNull(estimateRequest.getEstimate().getAuditDetails().getCreatedBy());
        assertNotNull(estimateRequest.getEstimate().getAuditDetails().getLastModifiedBy());
        assertNotNull(estimateRequest.getEstimate().getAuditDetails().getCreatedTime());
        assertNotNull(estimateRequest.getEstimate().getAuditDetails().getLastModifiedTime());
    }

    @Test
    void shouldGenerateEstimateId_IfSuccess() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        RequestInfo requestInfo = estimateRequest.getRequestInfo();
        lenient().when(idGenRepository.getId(eq(requestInfo), eq("pb"), eq(null), eq(null), eq(1)))
                .thenReturn(idGenerationResponse);

        enrichmentService.enrichEstimateOnCreate(estimateRequest);
        assertNotNull(estimateRequest.getEstimate().getId());
    }

    @Test
    void shouldGenerateEstimateNumber_IfSuccess() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        RequestInfo requestInfo = estimateRequest.getRequestInfo();
        lenient().when(idGenRepository.getId(eq(requestInfo), eq("pb"), eq(null), eq(null), eq(1)))
                .thenReturn(idGenerationResponse);
        enrichmentService.enrichEstimateOnCreate(estimateRequest);
        assertNotNull(estimateRequest.getEstimate().getId());
        assertNull(estimateRequest.getEstimate().getEstimateNumber());
    }

    @Test
    void shouldEnrichEstimateProposalDT_IfSuccess() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        RequestInfo requestInfo = estimateRequest.getRequestInfo();
        lenient().when(idGenRepository.getId(eq(requestInfo), eq("pb"), eq(null), eq(null), eq(1)))
                .thenReturn(idGenerationResponse);
        enrichmentService.enrichEstimateOnCreate(estimateRequest);
        assertNotNull(estimateRequest.getEstimate().getProposalDate());
    }

    @Test
    void shouldGenerateEstimateAddressId_IfSuccess() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        RequestInfo requestInfo = estimateRequest.getRequestInfo();
        lenient().when(idGenRepository.getId(eq(requestInfo), eq("pb"), eq(null), eq(null), eq(1)))
                .thenReturn(idGenerationResponse);
        enrichmentService.enrichEstimateOnCreate(estimateRequest);
        assertNotNull(estimateRequest.getEstimate().getAddress().getId());
    }

    @Test
    void shouldGenerateEstimateDetailId_IfSuccess() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        RequestInfo requestInfo = estimateRequest.getRequestInfo();
        lenient().when(idGenRepository.getId(eq(requestInfo), eq("pb"), eq(null), eq(null), eq(1)))
                .thenReturn(idGenerationResponse);
        enrichmentService.enrichEstimateOnCreate(estimateRequest);
        assertNotNull(estimateRequest.getEstimate().getEstimateDetails().get(0).getId());
    }

    @Test
    void shouldGenerateEstimateAmountDetailId_IfSuccess() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        RequestInfo requestInfo = estimateRequest.getRequestInfo();
        lenient().when(idGenRepository.getId(eq(requestInfo), eq("pb"), eq(null), eq(null), eq(1)))
                .thenReturn(idGenerationResponse);
        enrichmentService.enrichEstimateOnCreate(estimateRequest);
        assertNotNull(estimateRequest.getEstimate().getEstimateDetails().get(0).getAmountDetail().get(0).getId());
    }

}

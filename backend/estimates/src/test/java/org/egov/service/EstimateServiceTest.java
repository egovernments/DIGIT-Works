package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.helper.EstimateRequestBuilderTest;
import org.egov.producer.EstimateProducer;
import org.egov.repository.EstimateRepository;
import org.egov.tracer.model.CustomException;
import org.egov.validator.EstimateServiceValidator;
import org.egov.web.models.EstimateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@Slf4j
class EstimateServiceTest {

    @InjectMocks
    private EstimateService estimateService;

    @Mock
    private EstimateServiceConfiguration serviceConfiguration;

    @Mock
    private EstimateProducer producer;

    @Mock
    private EstimateServiceValidator serviceValidator;

    @Mock
    private EnrichmentService enrichmentService;

    @Mock
    private EstimateRepository estimateRepository;

    @Mock
    private WorkflowService workflowService;

    @Test
    void checkEstimateRequestCreate_IfValid() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateService.createEstimate(estimateRequest);
        assertNotNull(estimateRequest.getEstimate());
    }

    @Test
    void shouldThrowException_IfWorkflowServiceFails() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        lenient().when(workflowService.updateWorkflowStatus(any(EstimateRequest.class)))
                .thenThrow(new CustomException("BUSINESSSERVICE_DOESN'T_EXIST", ""));

        assertThrows(CustomException.class, () -> {
            estimateService.createEstimate(estimateRequest);
        });
    }

    @Test
    void shouldThrowException_IfEnrichmentServiceFails() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        doThrow(new CustomException("ENRICHMENT_FAILS", "Estimate enrichment failed")).when(enrichmentService)
                .enrichEstimateOnCreate(any(EstimateRequest.class));

        assertThrows(CustomException.class, () -> {
            estimateService.createEstimate(estimateRequest);
        });
    }

    @Test
    void shouldThrowException_IfValidatorFails() {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        doThrow(new CustomException("VALIDATOR_FAILS", "Estimate validator failed")).when(serviceValidator)
                .validateEstimateOnCreate(any(EstimateRequest.class));

        assertThrows(CustomException.class, () -> {
            estimateService.createEstimate(estimateRequest);
        });
    }
}
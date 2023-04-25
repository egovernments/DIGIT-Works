package org.egov.service;

import digit.models.coremodels.IdGenerationResponse;
import digit.models.coremodels.IdResponse;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.helper.MusterRollRequestBuilderTest;
import org.egov.kafka.Producer;
import org.egov.repository.IdGenRepository;
import org.egov.repository.MusterRollRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.MdmsUtil;
import org.egov.util.MusterRollServiceUtil;
import org.egov.validator.MusterRollValidator;
import org.egov.web.models.MusterRoll;
import org.egov.web.models.MusterRollRequest;
import org.egov.web.models.MusterRollSearchCriteria;
import org.egov.web.models.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class MusterRollServiceTest {

    @InjectMocks
    private MusterRollService musterRollService;
    @Mock
    private MusterRollValidator musterRollValidator;
    @Mock
    private EnrichmentService enrichmentService;
    @Mock
    private CalculationService calculationService;
    @Mock
    private WorkflowService workflowService;
    @Mock
    private Producer producer;
    @Mock
    private MusterRollServiceConfiguration serviceConfiguration;
    @Mock
    private MusterRollRepository musterRollRepository;


    @Test
    void checkMusterRollRequestCreate_IfValid() {
        MusterRollRequest musterRollRequest = MusterRollRequestBuilderTest.builder().withMusterForCreateSuccess();
        List<MusterRoll> musterRolls = null;
        lenient().when(musterRollRepository.getMusterRoll(any(MusterRollSearchCriteria.class),any(ArrayList.class))).thenReturn(musterRolls);
        musterRollService.createMusterRoll(musterRollRequest);
        assertNotNull(musterRollRequest.getMusterRoll());
    }

    @Test
    void checkMusterRollRequestEstimate_IfValid() {
        MusterRollRequest musterRollRequest = MusterRollRequestBuilderTest.builder().withMusterForCreateSuccess();
        List<MusterRoll> musterRolls = null;
        lenient().when(musterRollRepository.getMusterRoll(any(MusterRollSearchCriteria.class),any(ArrayList.class))).thenReturn(musterRolls);
        musterRollService.estimateMusterRoll(musterRollRequest);
        assertNotNull(musterRollRequest.getMusterRoll());
    }

    @Test
    void shouldThrowException_IfDuplicateMusterRoll() {
        MusterRollRequest musterRollRequest = MusterRollRequestBuilderTest.builder().withMusterForCreateSuccess();
        List<MusterRoll> musterRolls = new ArrayList<>();
        musterRolls.add(musterRollRequest.getMusterRoll());
        List<String> registerIds = null;
        lenient().when(musterRollRepository.getMusterRoll(any(MusterRollSearchCriteria.class),eq(registerIds))).thenReturn(musterRolls);
        CustomException exception = assertThrows(CustomException.class, ()-> musterRollService.createMusterRoll(musterRollRequest));
        assertTrue(exception.getCode().contentEquals("DUPLICATE_MUSTER_ROLL"));
    }

    @Test
    void shouldThrowException_IfWorkflowServiceFails() {
        MusterRollRequest musterRollRequest = MusterRollRequestBuilderTest.builder().withMusterForCreateSuccess();
        List<MusterRoll> musterRolls = null;
        lenient().when(musterRollRepository.getMusterRoll(any(MusterRollSearchCriteria.class),any(ArrayList.class))).thenReturn(musterRolls);
        lenient().when(workflowService.updateWorkflowStatus(any(MusterRollRequest.class))).thenThrow(new CustomException("BUSINESSSERVICE_DOESN'T_EXIST",""));

        assertThrows(CustomException.class, () -> {
            musterRollService.createMusterRoll(musterRollRequest);
        });
    }

}

package org.egov.service;

import digit.models.coremodels.IdGenerationResponse;
import digit.models.coremodels.IdResponse;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.helper.MusterRollRequestBuilderTest;
import org.egov.repository.IdGenRepository;
import org.egov.repository.MusterRollRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.MdmsUtil;
import org.egov.util.MusterRollServiceUtil;
import org.egov.web.models.MusterRollRequest;
import org.egov.web.models.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class EnrichmentServiceTest {

    @InjectMocks
    private EnrichmentService enrichmentService;
    @Mock
    private MdmsUtil mdmsUtils;
    @Mock
    private MusterRollServiceConfiguration config;
    @Mock
    private MusterRollServiceUtil musterRollServiceUtil;
    @Mock
    private IdGenRepository idGenRepository;
    @Mock
    private MusterRollRepository musterRollRepository;


    @Test
    void shouldGenerateMusterRollId_IfSuccess(){
        idGenResponseSuccess();
        MusterRollRequest musterRollRequest = MusterRollRequestBuilderTest.builder().withMusterForCreateSuccess();
        enrichmentService.enrichMusterRollOnCreate(musterRollRequest);
        assertNotNull(musterRollRequest.getMusterRoll().getId());
    }

    @Test
    void shouldGenerateMusterRollNumber_IfSuccess(){
        idGenResponseSuccess();
        MusterRollRequest musterRollRequest = MusterRollRequestBuilderTest.builder().withMusterForCreateSuccess();
        enrichmentService.enrichMusterRollOnCreate(musterRollRequest);
        assertEquals(musterRollRequest.getMusterRoll().getMusterRollNumber(),"MR/2022-23/01/05/000131");
    }

    @Test
    void checkStatusAsActiveForEstimate(){
        MusterRollRequest musterRollRequest = MusterRollRequestBuilderTest.builder().withMusterForCreateSuccess();
        enrichmentService.enrichMusterRollOnEstimate(musterRollRequest);
        assertEquals(Status.ACTIVE.toString(),musterRollRequest.getMusterRoll().getStatus().toString());
    }

    @Test
    void shouldThrowException_IfIdgenFailed(){
        idGenResponseFailure();
        MusterRollRequest musterRollRequest = MusterRollRequestBuilderTest.builder().withMusterForCreateSuccess();
        CustomException exception = assertThrows(CustomException.class, ()-> enrichmentService.enrichMusterRollOnCreate(musterRollRequest));
        assertTrue(exception.getCode().contentEquals("IDGEN ERROR"));
    }


    void idGenResponseSuccess() {
        //MOCK Idgen Response
        IdResponse idResponse = IdResponse.builder().id("MR/2022-23/01/05/000131").build();
        List<IdResponse> idResponses = new ArrayList<>();
        idResponses.add(idResponse);
        IdGenerationResponse idGenerationResponse = IdGenerationResponse.builder().idResponses(idResponses).build();
        when(idGenRepository.getId(eq(MusterRollRequestBuilderTest.builder().getRequestInfo()), eq("pb"), eq(null), eq(""), eq(1)))
                .thenReturn(idGenerationResponse);
    }


    void idGenResponseFailure() {
        //MOCK Idgen Response
        List<IdResponse> idResponses = new ArrayList<>();
        IdGenerationResponse idGenerationResponse = IdGenerationResponse.builder().idResponses(idResponses).build();
        lenient().when(idGenRepository.getId(eq(MusterRollRequestBuilderTest.builder().getRequestInfo()), eq("pb"), eq(null), eq(""), eq(1)))
                .thenReturn(idGenerationResponse);
    }

}

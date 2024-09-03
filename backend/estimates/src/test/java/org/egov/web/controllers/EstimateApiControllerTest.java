package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.EstimateServiceMain;
import org.egov.TestConfiguration;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.helper.EstimateRequestBuilderTest;
import org.egov.repository.EstimateRepository;
import org.egov.service.EstimateService;
import org.egov.tracer.model.CustomException;
import org.egov.tracer.model.ErrorRes;
import org.egov.util.ResponseInfoCreator;
import org.egov.web.models.EstimateRequest;
import org.egov.web.models.EstimateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * API tests for EstimateApiController
 */
@ContextConfiguration(classes = EstimateServiceMain.class)
@WebMvcTest(EstimateApiController.class)
@Import({TestConfiguration.class})
@AutoConfigureMockMvc
class EstimateApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstimateService estimateService;

    @MockBean
    private ResponseInfoCreator responseInfoCreator;

    @MockBean
    private EstimateRepository estimateRepository;

    @Test
    void estimateV1CreatePostSuccess() throws Exception {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        ResponseInfo responseInfo = EstimateRequestBuilderTest.builder().getResponseInfo_Success();
        when(estimateService.createEstimate(any(EstimateRequest.class))).thenReturn(estimateRequest);
        when(responseInfoCreator.createResponseInfoFromRequestInfo(any(RequestInfo.class), eq(true)))
                .thenReturn(responseInfo);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(estimateRequest);
        MvcResult result = mockMvc.perform(post("/v1/_create").contentType(MediaType
                        .APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk()).andReturn();
        String responseStr = result.getResponse().getContentAsString();
        EstimateResponse response = objectMapper.readValue(responseStr,
                EstimateResponse.class);

        assertEquals(1, response.getEstimates().size());
        assertEquals("successful", response.getResponseInfo().getStatus());
    }

    @Test
    void estimateV1CreatePostFailure() throws Exception {
        EstimateRequest musterRollRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateExceptionForAmountDetail();
        ResponseInfo responseInfo = EstimateRequestBuilderTest.builder().getResponseInfo_Success();
        when(estimateService.createEstimate(any(EstimateRequest.class)))
                .thenThrow(new CustomException("ESTIMATE.DETAIL.AMOUNT.DETAILS", "Amount details are mandatory"));
        when(responseInfoCreator.createResponseInfoFromRequestInfo(any(RequestInfo.class), eq(true)))
                .thenReturn(responseInfo);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(musterRollRequest);
        MvcResult result = mockMvc.perform(post("/v1/_create").contentType(MediaType
                        .APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isBadRequest()).andReturn();
        String responseStr = result.getResponse().getContentAsString();
        ErrorRes response = objectMapper.readValue(responseStr,
                ErrorRes.class);

        assertEquals(1, response.getErrors().size());
        assertEquals("ESTIMATE.DETAIL.AMOUNT.DETAILS", response.getErrors().get(0).getCode());
    }

    @Test
    void fetchtEstimateBasedSearchCriteriaUsedSuccess() throws Exception {
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder()
                .requestInfo(EstimateRequestBuilderTest.builder().getRequestInfo())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestInfoWrapper);

        mockMvc.perform(post("/v1/_search").contentType(MediaType
                        .APPLICATION_JSON_UTF8).content(content).param("tenantId", "pb.amritsar"))
                .andExpect(status().isOk());
    }

    @Test
    void fetchtheEstimateSbasedonthesearchcriteriausedFailure() throws Exception {
        mockMvc.perform(post("/v1/_search").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateaEstimateSuccess() throws Exception {
        EstimateRequest estimateRequest = EstimateRequestBuilderTest.builder().withEstimateForCreateSuccess();
        estimateRequest.getEstimate().setId("96ba2d55-b7a4-41e2-8598-19a83d63c9a5");
        ResponseInfo responseInfo = EstimateRequestBuilderTest.builder().getResponseInfo_Success();
        when(estimateService.updateEstimate(any(EstimateRequest.class))).thenReturn(estimateRequest);
        when(responseInfoCreator.createResponseInfoFromRequestInfo(any(RequestInfo.class), eq(true)))
                .thenReturn(responseInfo);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(estimateRequest);
        MvcResult result = mockMvc.perform(post("/v1/_update").contentType(MediaType
                        .APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk()).andReturn();

        String responseStr = result.getResponse().getContentAsString();
        EstimateResponse response = objectMapper.readValue(responseStr,
                EstimateResponse.class);

        assertEquals(1, response.getEstimates().size());
        assertEquals("successful", response.getResponseInfo().getStatus());
    }

    @Test
    void updateanestimateFailure() throws Exception {
        mockMvc.perform(post("/v1/_update").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

}

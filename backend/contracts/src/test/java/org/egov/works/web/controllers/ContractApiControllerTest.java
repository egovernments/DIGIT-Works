package org.egov.works.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.tracer.model.CustomException;
import org.egov.tracer.model.ErrorRes;
import org.egov.works.ContractServiceMain;
import org.egov.works.TestConfiguration;
import org.egov.works.helper.ContractRequestTestBuilder;
import org.egov.works.helper.ContractTestBuilder;
import org.egov.works.repository.AmountBreakupRepository;
import org.egov.works.repository.ContractRepository;
import org.egov.works.repository.DocumentRepository;
import org.egov.works.repository.LineItemsRepository;
import org.egov.works.service.ContractService;
import org.egov.works.web.models.ContractRequest;
import org.egov.works.web.models.ContractResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * API tests for ContractApiController
 */

@ContextConfiguration(classes = ContractServiceMain.class)
@WebMvcTest(ContractApiController.class)
@Import(TestConfiguration.class)
@AutoConfigureMockMvc
public class ContractApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcTemplate jdbcTemplate;


    @MockBean
    private ContractService contractService;

    @MockBean
    private AmountBreakupRepository amountBreakupRepository;

    @MockBean
    private ContractRepository contractRepository;

    @MockBean
    private DocumentRepository documentRepository;

    @MockBean
    private LineItemsRepository lineItemsRepository;



    @Test
    public void contractV1CreatePostSuccess() throws Exception {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().withWorkflow().build();
        ContractResponse contractResponse = ContractResponse.builder()
                .responseInfo(ResponseInfo.builder().apiId("some-apiId").ver("").ts(System.currentTimeMillis()).status("success").build())
                .contracts(Collections.singletonList(ContractTestBuilder.builder().withValidContract().build())).build();
        lenient().when(contractService.createContract(ArgumentMatchers.any(ContractRequest.class))).thenReturn(contractResponse);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(contractRequest);
        MvcResult result = mockMvc.perform(post("/v1/_create").contentType(MediaType.APPLICATION_JSON_UTF8).content(content)).andExpect(status().isOk()).andReturn();
        String responseString = result.getResponse().getContentAsString();
        ContractResponse response = objectMapper.readValue(responseString, ContractResponse.class);
        assertEquals("success", response.getResponseInfo().getStatus());

    }

    @Test
    public void contractV1CreatePostFaileds() throws Exception {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().withWorkflow().build();
        contractRequest.getRequestInfo().setUserInfo(null);
        lenient().when(contractService.createContract(ArgumentMatchers.any(ContractRequest.class))).thenThrow(new CustomException("USERINFO", "UserInfo is mandatory"));
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(contractRequest);
        MvcResult result = mockMvc.perform(post("/v1/_create").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)).andExpect(status().isBadRequest()).andReturn();
        String responseString = result.getResponse().getContentAsString();
        ErrorRes response = objectMapper.readValue(responseString, ErrorRes.class);
        assertEquals("UserInfo is mandatory", response.getErrors().get(0).getMessage());

    }

    @Test
    public void contractV1UpdatePostSuccess() throws Exception {
        ContractRequest contractRequest = ContractRequestTestBuilder.builder().withRequestInfo().withContract().withWorkflow().build();
        ContractResponse contractResponse = ContractResponse.builder()
                .responseInfo(ResponseInfo.builder().apiId("some-apiId").ver("").ts(System.currentTimeMillis()).status("success").build())
                .contracts(Collections.singletonList(ContractTestBuilder.builder().withValidContract().build())).build();
        lenient().when(contractService.updateContract(ArgumentMatchers.any(ContractRequest.class))).thenReturn(contractResponse);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(contractRequest);
        MvcResult result = mockMvc.perform(post("/v1/_update").contentType(MediaType.APPLICATION_JSON_UTF8).content(content)).andExpect(status().isOk()).andReturn();
        String responseString = result.getResponse().getContentAsString();
        ContractResponse response = objectMapper.readValue(responseString, ContractResponse.class);
        assertEquals("success", response.getResponseInfo().getStatus());

    }

    @Test
    public void contractV1CreatePostFailure() throws Exception {
        mockMvc.perform(post("/v1/_create").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void contractV1SearchPostSuccess() throws Exception {
        mockMvc.perform(post("/v1/_search").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void contractV1SearchPostFailure() throws Exception {
        mockMvc.perform(post("/v1/_search").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void contractV1UpdatePostFailure() throws Exception {
        mockMvc.perform(post("/v1/_update").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

}

package org.egov.works.web.controllers;

import org.egov.works.TestConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * API tests for ExchangeApiController
 */
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(ExchangeApiController.class)
@Import(TestConfiguration.class)
public class ExchangeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void exchangeV1AllocationPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/allocation").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1AllocationPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/allocation").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1BillPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/bill").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1BillPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/bill").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1DemandPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/demand").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1DemandPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/demand").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1DisbusePostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/disbuse").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1DisbusePostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/disbuse").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1OnAllocationPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-allocation").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1OnAllocationPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-allocation").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1OnBillPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-bill").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1OnBillPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-bill").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1OnDemandPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-demand").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1OnDemandPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-demand").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1OnDisbusePostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-disbuse").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1OnDisbusePostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-disbuse").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1OnProgramPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-program").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1OnProgramPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-program").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1OnReceiptPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-receipt").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1OnReceiptPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-receipt").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1OnSanctionPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-sanction").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1OnSanctionPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/on-sanction").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1ProgramPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/program").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1ProgramPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/program").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1ReceiptPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/receipt").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1ReceiptPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/receipt").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void exchangeV1SanctionPostSuccess() throws Exception {
        mockMvc.perform(post("/exchange/v1/sanction").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void exchangeV1SanctionPostFailure() throws Exception {
        mockMvc.perform(post("/exchange/v1/sanction").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

}

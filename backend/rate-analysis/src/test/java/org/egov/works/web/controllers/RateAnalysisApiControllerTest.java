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
 * API tests for RateAnalysisApiController
 */
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(RateAnalysisApiController.class)
@Import(TestConfiguration.class)
public class RateAnalysisApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void rateAnalysisV1CalculatePostSuccess() throws Exception {
        mockMvc.perform(post("/rate-analysis/v1/_calculate").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void rateAnalysisV1CalculatePostFailure() throws Exception {
        mockMvc.perform(post("/rate-analysis/v1/_calculate").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void rateAnalysisV1CreatePostSuccess() throws Exception {
        mockMvc.perform(post("/rate-analysis/v1/_create").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void rateAnalysisV1CreatePostFailure() throws Exception {
        mockMvc.perform(post("/rate-analysis/v1/_create").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void rateAnalysisV1SchedulerCreatePostSuccess() throws Exception {
        mockMvc.perform(post("/rate-analysis/v1/scheduler/_create").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void rateAnalysisV1SchedulerCreatePostFailure() throws Exception {
        mockMvc.perform(post("/rate-analysis/v1/scheduler/_create").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void rateAnalysisV1SchedulerSearchPostSuccess() throws Exception {
        mockMvc.perform(post("/rate-analysis/v1/scheduler/_search").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void rateAnalysisV1SchedulerSearchPostFailure() throws Exception {
        mockMvc.perform(post("/rate-analysis/v1/scheduler/_search").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

}

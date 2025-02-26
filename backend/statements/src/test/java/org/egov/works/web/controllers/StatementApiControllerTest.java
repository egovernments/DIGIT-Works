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
* API tests for StatementApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(StatementApiController.class)
@Import(TestConfiguration.class)
public class StatementApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void statementV1AnalysisCreatePostSuccess() throws Exception {
        mockMvc.perform(post("/statement/v1/analysis/create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void statementV1AnalysisCreatePostFailure() throws Exception {
        mockMvc.perform(post("/statement/v1/analysis/create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void statementV1AnalysisSearchPostSuccess() throws Exception {
        mockMvc.perform(post("/statement/v1/analysis/search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void statementV1AnalysisSearchPostFailure() throws Exception {
        mockMvc.perform(post("/statement/v1/analysis/search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void statementV1UtilizationCreatePostSuccess() throws Exception {
        mockMvc.perform(post("/statement/v1/utilization/create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void statementV1UtilizationCreatePostFailure() throws Exception {
        mockMvc.perform(post("/statement/v1/utilization/create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void statementV1UtilizationSearchPostSuccess() throws Exception {
        mockMvc.perform(post("/statement/v1/utilization/search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void statementV1UtilizationSearchPostFailure() throws Exception {
        mockMvc.perform(post("/statement/v1/utilization/search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

}

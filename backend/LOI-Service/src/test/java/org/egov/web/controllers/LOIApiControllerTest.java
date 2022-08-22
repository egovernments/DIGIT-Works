package org.egov.web.controllers;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.egov.TestConfiguration;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* API tests for LOIApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(LOIApiController.class)
@Import(TestConfiguration.class)
public class LOIApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void letterOfIndentV1CreatePostSuccess() throws Exception {
        mockMvc.perform(post("/eGovTrial/LOI-Service/1.0.0/letter-of-indent/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void letterOfIndentV1CreatePostFailure() throws Exception {
        mockMvc.perform(post("/eGovTrial/LOI-Service/1.0.0/letter-of-indent/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void letterOfIndentV1SearchPostSuccess() throws Exception {
        mockMvc.perform(post("/eGovTrial/LOI-Service/1.0.0/letter-of-indent/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void letterOfIndentV1SearchPostFailure() throws Exception {
        mockMvc.perform(post("/eGovTrial/LOI-Service/1.0.0/letter-of-indent/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void letterOfIndentV1UpdatePostSuccess() throws Exception {
        mockMvc.perform(post("/eGovTrial/LOI-Service/1.0.0/letter-of-indent/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void letterOfIndentV1UpdatePostFailure() throws Exception {
        mockMvc.perform(post("/eGovTrial/LOI-Service/1.0.0/letter-of-indent/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

}

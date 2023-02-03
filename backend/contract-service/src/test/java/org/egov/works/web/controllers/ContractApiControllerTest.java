package org.egov.works.web.controllers;

import org.egov.works.TestConfiguration;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* API tests for ContractApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(ContractApiController.class)
@Import(TestConfiguration.class)
public class ContractApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contractV1CreatePostSuccess() throws Exception {
        mockMvc.perform(post("/contract/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void contractV1CreatePostFailure() throws Exception {
        mockMvc.perform(post("/contract/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void contractV1SearchPostSuccess() throws Exception {
        mockMvc.perform(post("/contract/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void contractV1SearchPostFailure() throws Exception {
        mockMvc.perform(post("/contract/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void contractV1UpdatePostSuccess() throws Exception {
        mockMvc.perform(post("/contract/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void contractV1UpdatePostFailure() throws Exception {
        mockMvc.perform(post("/contract/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

}

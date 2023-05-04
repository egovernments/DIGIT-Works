package org.egov.digit.expense.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.egov.digit.expense.TestConfiguration;
import org.egov.digit.expense.web.controller.BillController;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
* API tests for BillApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(BillController.class)
@Import(TestConfiguration.class)
public class BillApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void billV1CreatePostSuccess() throws Exception {
        mockMvc.perform(post("/bill/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void billV1CreatePostFailure() throws Exception {
        mockMvc.perform(post("/bill/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void billV1SearchPostSuccess() throws Exception {
        mockMvc.perform(post("/bill/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void billV1SearchPostFailure() throws Exception {
        mockMvc.perform(post("/bill/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void billV1UpdatePostSuccess() throws Exception {
        mockMvc.perform(post("/bill/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void billV1UpdatePostFailure() throws Exception {
        mockMvc.perform(post("/bill/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

}

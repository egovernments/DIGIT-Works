package org.egov.works.web.controllers;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.egov.works.TestConfiguration;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* API tests for FacilityApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(FacilityApiController.class)
@Import(TestConfiguration.class)
public class FacilityApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void facilityV1CreatePostSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Project-Service/1.0.0/facility/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void facilityV1CreatePostFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Project-Service/1.0.0/facility/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void facilityV1SearchPostSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Project-Service/1.0.0/facility/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void facilityV1SearchPostFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Project-Service/1.0.0/facility/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void facilityV1UpdatePostSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Project-Service/1.0.0/facility/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void facilityV1UpdatePostFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Project-Service/1.0.0/facility/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

}

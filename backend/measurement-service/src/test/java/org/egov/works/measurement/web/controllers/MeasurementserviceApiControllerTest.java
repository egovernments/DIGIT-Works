package org.egov.works.measurement.web.controllers;

import org.egov.works.measurement.TestConfiguration;
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
 * API tests for MeasurementserviceApiController
 */
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(MeasurementserviceApiController.class)
@Import(TestConfiguration.class)
public class MeasurementserviceApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void measurementserviceV1CreatePostSuccess() throws Exception {
        mockMvc.perform(post("/measurementservice/v1/_create").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void measurementserviceV1CreatePostFailure() throws Exception {
        mockMvc.perform(post("/measurementservice/v1/_create").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void measurementserviceV1SearchPostSuccess() throws Exception {
        mockMvc.perform(post("/measurementservice/v1/_search").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void measurementserviceV1SearchPostFailure() throws Exception {
        mockMvc.perform(post("/measurementservice/v1/_search").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void measurementserviceV1UpdatePostSuccess() throws Exception {
        mockMvc.perform(post("/measurementservice/v1/_update").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void measurementserviceV1UpdatePostFailure() throws Exception {
        mockMvc.perform(post("/measurementservice/v1/_update").contentType(MediaType
                        .APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

}

package org.egov.digit.web.controllers;

import org.egov.digit.web.models.DemandRequest;
import org.egov.digit.web.models.DemandResponse;
import org.egov.digit.web.models.DemandSearchRequest;
import org.egov.digit.web.models.ErrorRes;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.egov.digit.TestConfiguration;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* API tests for DemandApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(DemandApiController.class)
@Import(TestConfiguration.class)
public class DemandApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void demandV1CreatePostSuccess() throws Exception {
        mockMvc.perform(post("/demand/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void demandV1CreatePostFailure() throws Exception {
        mockMvc.perform(post("/demand/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void demandV1SearchPostSuccess() throws Exception {
        mockMvc.perform(post("/demand/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void demandV1SearchPostFailure() throws Exception {
        mockMvc.perform(post("/demand/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void demandV1UpdatePostSuccess() throws Exception {
        mockMvc.perform(post("/demand/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void demandV1UpdatePostFailure() throws Exception {
        mockMvc.perform(post("/demand/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

}

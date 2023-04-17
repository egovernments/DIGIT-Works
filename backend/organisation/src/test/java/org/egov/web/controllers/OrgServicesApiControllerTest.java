package org.egov.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.egov.TestConfiguration;

/**
* API tests for OrgServicesApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(OrgServicesApiController.class)
@Import(TestConfiguration.class)
public class OrgServicesApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void orgServicesOrganisationV1CreatePOSTSuccess() throws Exception {
        mockMvc.perform(post("/org-services/organisation/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void orgServicesOrganisationV1CreatePOSTFailure() throws Exception {
        mockMvc.perform(post("/org-services/organisation/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void orgServicesOrganisationV1SearchPOSTSuccess() throws Exception {
        mockMvc.perform(post("/org-services/organisation/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void orgServicesOrganisationV1SearchPOSTFailure() throws Exception {
        mockMvc.perform(post("/org-services/organisation/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void orgServicesOrganisationV1UpdatePOSTSuccess() throws Exception {
        mockMvc.perform(post("/org-services/organisation/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void orgServicesOrganisationV1UpdatePOSTFailure() throws Exception {
        mockMvc.perform(post("/org-services/organisation/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void orgServicesV1CreatePOSTSuccess() throws Exception {
        mockMvc.perform(post("/org-services/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void orgServicesV1CreatePOSTFailure() throws Exception {
        mockMvc.perform(post("/org-services/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void orgServicesV1SearchPOSTSuccess() throws Exception {
        mockMvc.perform(post("/org-services/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void orgServicesV1SearchPOSTFailure() throws Exception {
        mockMvc.perform(post("/org-services/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void orgServicesV1UpdatePOSTSuccess() throws Exception {
        mockMvc.perform(post("/org-services/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void orgServicesV1UpdatePOSTFailure() throws Exception {
        mockMvc.perform(post("/org-services/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

}

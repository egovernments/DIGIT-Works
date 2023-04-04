package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.Main;
import org.egov.TestConfiguration;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.config.Configuration;
import org.egov.helper.OrganisationRequestTestBuilder;
import org.egov.kafka.Producer;
import org.egov.repository.OrganisationRepository;
import org.egov.service.OrganisationEnrichmentService;
import org.egov.service.OrganisationService;
import org.egov.service.UserService;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.OrganisationServiceValidator;
import org.egov.web.models.OrgRequest;
import org.egov.web.models.OrgResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import javax.servlet.http.HttpServletRequest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@WebMvcTest(OrganisationApiController.class)
//@Import({TestConfiguration.class})
//@Slf4j
@ContextConfiguration(classes = Main.class)
@WebMvcTest(OrganisationApiController.class)
@Import(TestConfiguration.class)
@AutoConfigureMockMvc
public class OrganisationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @InjectMocks
//    @Autowired
    @MockBean
    private OrganisationService organisationService;

    @MockBean
    private ResponseInfoFactory responseInfoFactory;

    @MockBean
    private OrganisationEnrichmentService organisationEnrichmentService;

    @MockBean
    private OrganisationServiceValidator organisationServiceValidator;

    @MockBean
    private OrganisationRepository organisationRepository;

    @MockBean
    private Producer producer;

    @MockBean
    private Configuration configuration;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Organisation request should pass with API Operation CREATE")
    public void createProjectPostSuccess() throws Exception {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        when(organisationService.createOrganisationWithoutWorkFlow(any(OrgRequest.class))).thenReturn(orgRequest);

        ResponseInfo responseInfo = ResponseInfo.builder()
                .apiId(orgRequest.getRequestInfo().getApiId())
                .ver(orgRequest.getRequestInfo().getVer())
                .ts(orgRequest.getRequestInfo().getTs())
                .resMsgId("uief87324")
                .msgId(orgRequest.getRequestInfo().getMsgId())
                .status("successful").build();
        when(responseInfoFactory.createResponseInfoFromRequestInfo(orgRequest.getRequestInfo(),true)).thenReturn(responseInfo);

        MvcResult result = mockMvc.perform(post("/organisation/v1/_create").contentType(MediaType
                        .APPLICATION_JSON).content(objectMapper.writeValueAsString(orgRequest)))
                .andExpect(status().isOk()).andReturn();

        String responseStr = result.getResponse().getContentAsString();
        OrgResponse response = objectMapper.readValue(responseStr,
                OrgResponse.class);

        assertEquals(1, response.getOrganisations().size());
        assertNotNull(response.getOrganisations().get(0).getName());
        assertEquals("successful", response.getResponseInfo().getStatus());
    }

}

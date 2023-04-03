package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.TestConfiguration;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.web.models.OrgRequest;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrganisationApiController.class)
@Import({TestConfiguration.class})
@Slf4j
public class OrganisationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Organisation request should pass with API Operation CREATE")
    public void createProjectPostSuccess() throws Exception {
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodProject().build();
        when(projectService.createProject(any(ProjectRequest.class))).thenReturn(projectRequest);

        ResponseInfo responseInfo = ResponseInfo.builder()
                .apiId(projectRequest.getRequestInfo().getApiId())
                .ver(projectRequest.getRequestInfo().getVer())
                .ts(projectRequest.getRequestInfo().getTs())
                .resMsgId("uief87324")
                .msgId(projectRequest.getRequestInfo().getMsgId())
                .status("successful").build();
        when(responseInfoFactory.createResponseInfoFromRequestInfo(projectRequest.getRequestInfo(),true)).thenReturn(responseInfo);

        MvcResult result = mockMvc.perform(post("/project/v1/_create").contentType(MediaType
                        .APPLICATION_JSON).content(objectMapper.writeValueAsString(projectRequest)))
                .andExpect(status().isOk()).andReturn();

        String responseStr = result.getResponse().getContentAsString();
        ProjectResponse response = objectMapper.readValue(responseStr,
                ProjectResponse.class);

        assertEquals(1, response.getProject().size());
        assertNotNull(response.getProject().get(0).getId());
        assertEquals("successful", response.getResponseInfo().getStatus());
    }

}

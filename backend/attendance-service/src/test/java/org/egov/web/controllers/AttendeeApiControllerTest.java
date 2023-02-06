package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.Main;
import org.egov.TestConfiguration;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.helper.AttendeeRequestBuilderTest;
import org.egov.service.AttendeeService;
import org.egov.tracer.model.CustomException;
import org.egov.util.ResponseInfoFactory;
import org.egov.web.models.AttendeeCreateRequest;
import org.egov.web.models.AttendeeCreateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.HttpStatusCodeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Import({TestConfiguration.class})
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AttendeeApiControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AttendeeService attendeeService;

    @MockBean
    private ResponseInfoFactory responseInfoFactory;

    @Test
    @DisplayName("should pass for correct API operation")
    public void attendeeCreatePostSuccess() throws Exception {

        AttendeeCreateRequest attendeeCreateRequest = AttendeeRequestBuilderTest.getAttendeeCreateRequest();
        ResponseInfo responseInfo = AttendeeRequestBuilderTest.getResponseInfo_Success();

        when(attendeeService.createAttendee(any(AttendeeCreateRequest.class))).thenReturn(attendeeCreateRequest);
        when(responseInfoFactory.createResponseInfoFromRequestInfo(any(RequestInfo.class), eq(true))).thenReturn(responseInfo);

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(attendeeCreateRequest);
        MvcResult result = mockMvc.perform(post("/attendee/v1/_create").contentType(MediaType
                        .APPLICATION_JSON).content(content))
                .andExpect(status().isOk()).andReturn();
        String responseStr = result.getResponse().getContentAsString();

        AttendeeCreateResponse response = objectMapper.readValue(responseStr, AttendeeCreateResponse.class);

        assertEquals("successful", response.getResponseInfo().getStatus());

    }

    @Test
    @DisplayName("should fail for incomplete attendee object in API request")
    public void attendeeCreatePostFailure() throws Exception {

        AttendeeCreateRequest attendeeCreateRequest = AttendeeRequestBuilderTest.getAttendeeCreateRequest();
        ResponseInfo responseInfo = AttendeeRequestBuilderTest.getResponseInfo_Success();

        attendeeCreateRequest.getAttendees().get(0).setIndividualId(null);

        when(attendeeService.createAttendee(any(AttendeeCreateRequest.class))).thenThrow(CustomException.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(attendeeCreateRequest);
        MvcResult result = mockMvc.perform(post("/attendee/v1/_create").contentType(MediaType
                        .APPLICATION_JSON).content(content))
                .andExpect(status().isBadRequest()).andReturn();
    }

}

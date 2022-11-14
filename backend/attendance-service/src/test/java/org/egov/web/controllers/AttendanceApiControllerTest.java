package org.egov.web.controllers;

import org.egov.web.models.AttendanceLogRequest;
import org.egov.web.models.AttendanceLogResponse;
import org.egov.web.models.AttendanceRegisterRequest;
import org.egov.web.models.AttendanceRegisterResponse;
import org.egov.web.models.AttendeeCreateRequest;
import org.egov.web.models.AttendeeCreateResponse;
import org.egov.web.models.AttendeeDeleteRequest;
import org.egov.web.models.AttendeeDeleteResponse;
import org.egov.web.models.ErrorRes1;
import org.egov.web.models.RequestInfoWrapper;
import org.egov.web.models.StaffPermissionRequest;
import org.egov.web.models.StaffPermissionResponse;
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
import org.egov.TestConfiguration;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* API tests for AttendanceApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(AttendanceApiController.class)
@Import(TestConfiguration.class)
public class AttendanceApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void attendanceAttendeeV1CreatePOSTSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/attendee/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void attendanceAttendeeV1CreatePOSTFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/attendee/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void attendanceAttendeeV1DeletePOSTSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/attendee/v1/_delete").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void attendanceAttendeeV1DeletePOSTFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/attendee/v1/_delete").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void attendanceLogV1CreatePOSTSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/log/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void attendanceLogV1CreatePOSTFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/log/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void attendanceLogV1SearchPOSTSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/log/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void attendanceLogV1SearchPOSTFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/log/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void attendanceLogV1UpdatePOSTSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/log/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void attendanceLogV1UpdatePOSTFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/log/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void attendanceStaffV1CreatePOSTSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/staff/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void attendanceStaffV1CreatePOSTFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/staff/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void attendanceStaffV1DeletePOSTSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/staff/v1/_delete").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void attendanceStaffV1DeletePOSTFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/staff/v1/_delete").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void attendanceV1CreatePOSTSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void attendanceV1CreatePOSTFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void attendanceV1SearchPOSTSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void attendanceV1SearchPOSTFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void attendanceV1UpdatePOSTSuccess() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void attendanceV1UpdatePOSTFailure() throws Exception {
        mockMvc.perform(post("/rushang.dhanesha/Attendance-Service/1.0.0/attendance/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

}

package org.egov.web.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.egov.common.contract.request.RequestInfo;

public class AttendanceRegisterRequest {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;
    @JsonProperty("attendanceRegister")
    private List<AttendanceRegister> attendanceRegister = null;

    public static AttendanceRegisterRequestBuilder builder() {
        return new AttendanceRegisterRequestBuilder();
    }

    public RequestInfo getRequestInfo() {
        return this.requestInfo;
    }

    public List<AttendanceRegister> getAttendanceRegister() {
        return this.attendanceRegister;
    }

    @JsonProperty("RequestInfo")
    public void setRequestInfo(final RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    @JsonProperty("attendanceRegister")
    public void setAttendanceRegister(final List<AttendanceRegister> attendanceRegister) {
        this.attendanceRegister = attendanceRegister;
    }

    public AttendanceRegisterRequest(final RequestInfo requestInfo, final List<AttendanceRegister> attendanceRegister) {
        this.requestInfo = requestInfo;
        this.attendanceRegister = attendanceRegister;
    }

    public AttendanceRegisterRequest() {
    }

    public static class AttendanceRegisterRequestBuilder {
        private RequestInfo requestInfo;
        private List<AttendanceRegister> attendanceRegister;

        AttendanceRegisterRequestBuilder() {
        }

        @JsonProperty("RequestInfo")
        public AttendanceRegisterRequestBuilder requestInfo(final RequestInfo requestInfo) {
            this.requestInfo = requestInfo;
            return this;
        }

        @JsonProperty("attendanceRegister")
        public AttendanceRegisterRequestBuilder attendanceRegister(final List<AttendanceRegister> attendanceRegister) {
            this.attendanceRegister = attendanceRegister;
            return this;
        }

        public AttendanceRegisterRequest build() {
            return new AttendanceRegisterRequest(this.requestInfo, this.attendanceRegister);
        }

        public String toString() {
            return "AttendanceRegisterRequest.AttendanceRegisterRequestBuilder(requestInfo=" + this.requestInfo + ", attendanceRegister=" + this.attendanceRegister + ")";
        }
    }
}


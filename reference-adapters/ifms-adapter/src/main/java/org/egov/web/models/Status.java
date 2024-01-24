package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.*;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.enums.StatusCode;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @JsonProperty("status_code")
    StatusCode statusCode;

    @JsonProperty("status_message")
    String statusMessage;

    static public Status fromString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(json, Status.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException("Error parsing VerificationRequest fromString", e.toString());
        }
    }
}
package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import org.egov.common.models.individual.Gender;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.constants.Error;

import jakarta.validation.constraints.Size;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Individual {
    @JsonProperty("pin")
    String pin;

    @JsonProperty("name")
    @Size(min = 1, message = Error.INVALID_NAME)
    String name;

    @JsonProperty("gender")
    @Size(min = 1, message = Error.INVALID_GENDER)
    Gender gender;

    @JsonProperty("address")
    @Size(min = 1, message = Error.INVALID_ADDRESS)
    String address;

    @JsonProperty("email")
    @Size(min = 1, message = Error.INVALID_EMAIL)
    String email;

    @JsonProperty("phone")
    @Size(min = 1, message = Error.INVALID_PHONE)
    String phone;
}

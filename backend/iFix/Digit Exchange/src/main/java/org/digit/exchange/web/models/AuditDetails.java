package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.ZonedDateTimeConverter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Embeddable
public class AuditDetails {
    @NotNull
    @JsonProperty("created_by")
    @NotNull
    private String createdBy;
    @JsonProperty("created_on")  
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime createdOn;

    public AuditDetails(){}

    static public AuditDetails fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, AuditDetails.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing AuditDetails fromString", e);
		}
	}
}

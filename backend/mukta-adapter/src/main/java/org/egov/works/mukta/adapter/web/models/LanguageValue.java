package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.egov.tracer.model.CustomException;

@Getter
@Setter
@Builder
public class LanguageValue {
    @JsonProperty("language")
	private String language;
    @JsonProperty("value")
	private String value;

    static public LanguageValue fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, LanguageValue.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing LanguageValue fromString", e.toString());
		}
	}
}

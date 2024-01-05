package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.constants.Gender;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.LanguageValueListConverter;
import org.digit.exchange.constants.Error;

import java.util.List;


@Getter
@Setter
@Embeddable
public class Individual extends ExchangeMessage{
    @JsonProperty("pin")
	@NotBlank(message = Error.INVALID_PIN)
	String pin;
	
    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("name")
	@Size(min = 1, message = Error.INVALID_NAME)
	List<LanguageValue> name;

    @JsonProperty("gender")
	@Size(min = 1, message = Error.INVALID_GENDER)
	Gender gender;

	@JsonProperty("address")
	@Size(min = 1, message = Error.INVALID_ADDRESS)
	Address address;

	// @JsonProperty("encoded_photo")
	// @NotBlank(message = Error.INVALID_ENCODED_PHOTO)
	// String encodedPhoto;

    @JsonProperty("email")
	@Size(min = 1, message = Error.INVALID_EMAIL)
	String email;

    @JsonProperty("phone")
	@Size(min = 1, message = Error.INVALID_PHONE)
	String phone;
	
	static public Individual fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Individual.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Identity fromString", e);
		}
	}
}

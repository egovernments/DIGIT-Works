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
import org.digit.exchange.constants.Error;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.LanguageValueListConverter;

import java.util.List;


@Getter
@Setter
@Embeddable
public class Organisation extends ExchangeMessage {
    @JsonProperty("pin")
	@NotBlank(message = Error.INVALID_PIN)
	String pin;

    @JsonProperty("organisation_type")
	@NotBlank(message = Error.INVALID_ORGANISATION_TYPE)
	String orgType;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("name")
	@Size(min = 1, message = Error.INVALID_NAME)
	List<LanguageValue> name;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("full_name")
	@Size(min = 1, message = Error.INVALID_FULLNAME)
	List<LanguageValue> fullName;
	
    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("street_address")
	@Size(min = 1, message = Error.INVALID_STREET_ADDRESS)
	List<LanguageValue> streetAddress;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("locality")
	@Size(min = 1, message = Error.INVALID_LOCALITY)
	List<LanguageValue> locality;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("region")
	@Size(min = 1, message = Error.INVALID_REGION)
	List<LanguageValue> region;

    @JsonProperty("postal_code")
	@Size(min = 1, message = Error.INVALID_POSTAL_CODE)
	String postalCode;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("country")
	@Size(min = 1, message = Error.INVALID_COUNTRY)
	List<LanguageValue> country;

    @JsonProperty("admin_id")
	@NotBlank(message = Error.INVALID_ID)
	String adminId;

    @JsonProperty("email")
	@Size(min = 1, message = Error.INVALID_EMAIL)
	String email;

    @JsonProperty("phone")
	@Size(min = 1, message = Error.INVALID_PHONE)
	String phone;
	
    @JsonProperty("zoneInfo")
	@Size(min = 1, message = Error.INVALID_ZONEINFO)
	String zoneInfo;
	
    @JsonProperty("locale")
	@Size(min = 1, message = Error.INVALID_LOCALE)
	String locale;

    static public Organisation fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Organisation.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Organisation fromString", e);
		}
	}

}

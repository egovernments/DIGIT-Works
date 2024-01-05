package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.LanguageValueListConverter;

import java.util.List;

@Embeddable
@Getter
@Setter
public class Address {
    
    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("doorNo")
    private List<LanguageValue> doorNo = null;

    @JsonProperty("latitude")
    private Double latitude = null;

    @JsonProperty("longitude")
    private Double longitude = null;

    @JsonProperty("locationAccuracy")
    private Double locationAccuracy = null;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("addressNumber")
    private List<LanguageValue> addressNumber = null;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("addressLine1")
    private List<LanguageValue> addressLine1 = null;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("addressLine2")
    private List<LanguageValue> addressLine2 = null;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("landmark")
    private List<LanguageValue> landmark = null;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("city")
    private List<LanguageValue> city = null;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("pincode")
    private List<LanguageValue> pincode = null;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("detail")
    private List<LanguageValue> detail = null;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("buildingName")
    private List<LanguageValue> buildingName = null;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("street")
    private List<LanguageValue> street = null;

    @JsonProperty("boundaryType")
    private String boundaryType = null;

    @JsonProperty("boundary")
    private String boundary = null;

    static public Address fromString(String json){
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      try {
        return mapper.readValue(json, Address.class);
      } catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Address fromString", e);
		}
	}
}

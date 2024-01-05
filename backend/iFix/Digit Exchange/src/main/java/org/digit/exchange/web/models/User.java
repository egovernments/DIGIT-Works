package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.LanguageValueListConverter;
import org.digit.exchange.constants.Error;

import java.util.List;



@Getter
@Setter
@Entity
@Table(name="users")
public class User{
    @Id
    @JsonProperty("id")
	String id;
	
    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("name")
    @NotNull
	@Size(min = 1, message = Error.INVALID_NAME)
	List<LanguageValue> name;

	@JsonProperty("address")
    @NotNull
    @Embedded
	@Size(min = 1, message = Error.INVALID_ADDRESS)
	Address address;

    @JsonProperty("email")
	String email;

    @JsonProperty("phone")
	String phone;

    @JsonProperty("pin")
	String pin;

    @JsonProperty("is_active")
	Boolean isActive;
	
	static public User fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing User fromString", e);
		}
	}
}

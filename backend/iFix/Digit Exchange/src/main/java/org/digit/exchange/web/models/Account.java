package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.LanguageValueListConverter;
import org.digit.exchange.constants.Error;

import java.util.List;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name="account")
public class Account{
    @Id
    @JsonProperty("id")
	private String id;

    @JsonProperty("account_type")
	@NotBlank(message = Error.INVALID_ORGANISATION_TYPE)
	private String accountType;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("name")
	@Size(min = 1, message = Error.INVALID_NAME)
	private List<LanguageValue> name;

    @JsonProperty("account_code")
	private String account_code;

    @JsonProperty("address")
    @Embedded
    @NotNull
	private Address address;

    @JsonProperty("administrator")
    @NotNull
	private String administratorId;


    static public Account fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Account.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Account fromString", e);
		}
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

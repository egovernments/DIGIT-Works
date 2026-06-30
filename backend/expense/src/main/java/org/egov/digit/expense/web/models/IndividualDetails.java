package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndividualDetails {

	@JsonProperty("id")
	private String id;

	@JsonProperty("userUuid")
	private String userUuid;

	@JsonProperty("name")
	@Valid
	private String name;

	@JsonProperty("phoneNumber")
	@NotNull
	@Valid
	private String phoneNumber;

	@JsonProperty("email")
	@Valid
	private String email;

	@JsonProperty("roles")
	@Valid
	private List<String> roles;

}

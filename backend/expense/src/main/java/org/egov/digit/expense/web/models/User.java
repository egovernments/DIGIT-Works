package org.egov.digit.expense.web.models;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is acting ID token of the authenticated user on the server. Any value
 * provided by the clients will be ignored and actual user based on authentication token
 * will be used on the server.
 */
@Schema(description = "This is acting ID token of the authenticated user on the server. Any value provided by the clients will be ignored and actual user based on authtoken will be used on the server.")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@JsonProperty("tenantId")
	@NotNull
	private String tenantId;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("uuid")
	private String uuid;

	@JsonProperty("userName")
	@NotNull
	private String userName;

	@JsonProperty("mobileNumber")
	private String mobileNumber;

	@JsonProperty("emailId")
	private String emailId;

	@JsonProperty("roles")
	@NotNull
	@Valid
	private List<Role> roles;

	public User addRolesItem(Role rolesItem) {
		this.roles.add(rolesItem);
		return this;
	}

}

package org.egov.digit.expense.web.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * minimal representation of the Roles in the system to be carried along in
 * UserInfo with RequestInfo meta data. Actual authorization service to extend
 * this to have more role related attributes
 */
@Schema(description = "minimal representation of the Roles in the system to be carried along in UserInfo with RequestInfo meta data. Actual authorization service to extend this to have more role related attributes ")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
	
	@JsonProperty("tenantId")
	@NotNull
	private String tenantId;

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	@Size(max = 64)
	private String name;

	@JsonProperty("description")
	private String description;

}

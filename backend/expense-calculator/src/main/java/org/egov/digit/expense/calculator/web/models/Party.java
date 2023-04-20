package org.egov.digit.expense.calculator.web.models;

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
 * Account details
 */
@Schema(description = "Account details")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Party {
	@JsonProperty("type")
	@NotNull
	@Size(min = 2, max = 64)
	private String type = null;

	@JsonProperty("tenantId")
	private String tenantId = null;

	@JsonProperty("identifier")
	@NotNull
	@Size(min = 2, max = 64)
	private String identifier = null;

}

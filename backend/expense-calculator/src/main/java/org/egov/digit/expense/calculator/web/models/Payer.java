package org.egov.digit.expense.calculator.web.models;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "A Object which holds the payer details")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payer {
	@JsonProperty("id")
	@Valid
	private String id = null;

	@JsonProperty("tenantId")
	@Valid
	private String tenantId = null;

	@JsonProperty("type")
	@Valid
	private String type = null;

	@JsonProperty("code")
	@Valid
	private String code = null;

	@JsonProperty("active")
	@Valid
	private Boolean active = null;

}

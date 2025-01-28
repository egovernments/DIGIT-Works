package org.egov.digit.expense.calculator.web.models;

import jakarta.validation.Valid;

import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CalculationRequest
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculationRequest {
	@JsonProperty("RequestInfo")
	@Valid
	private RequestInfo requestInfo = null;

	@JsonProperty("criteria")
	@Valid
	private Criteria criteria = null;

}

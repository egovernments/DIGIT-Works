package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

/**
 * CalculatorSearchRequest
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculatorSearchRequest {
	
	@JsonProperty("RequestInfo")
	@NotNull
	private RequestInfo requestInfo;

	@JsonProperty("searchCriteria")
	@NotNull
	private CalculatorSearchCriteria searchCriteria;

	@JsonProperty("pagination")
	@NotNull
	private Pagination pagination;

}

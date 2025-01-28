package org.egov.digit.expense.web.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillSearchRequest
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillSearchRequest {
	
	@JsonProperty("RequestInfo")
	@NotNull
	private RequestInfo requestInfo;

	@JsonProperty("billCriteria")
	@NotNull
	@Valid
	private BillCriteria billCriteria;

	@JsonProperty("pagination")
	@NotNull
	@Default
	private Pagination pagination = new Pagination();

}

package org.egov.digit.expense.web.models;

import jakarta.validation.constraints.NotNull;

import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PaymentSearchRequest
 */
@Schema(description = "A Object which holds the info about the payment criteria")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSearchRequest {

	@JsonProperty("RequestInfo")
	@NotNull
	private RequestInfo requestInfo;

	@JsonProperty("paymentCriteria")
	@NotNull
	private PaymentCriteria paymentCriteria;

	@JsonProperty("pagination")
	@Default
	private Pagination pagination = new Pagination();
}

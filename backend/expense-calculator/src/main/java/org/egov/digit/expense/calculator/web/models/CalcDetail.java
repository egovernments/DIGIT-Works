package org.egov.digit.expense.calculator.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bill details of the individual payees. billId will be empty in case of
 * estimate APIs. Will only be populated when bill is generated.
 */
@Schema(description = "Bill details of the individual payees. billId will be empty in case of estimate APIs. Will only be populated when bill is generated.")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalcDetail {
	@JsonProperty("id")

	@Valid
	private UUID id = null;

	@JsonProperty("billId")

	@Size(min = 2, max = 64)
	private String billId = null;

	@JsonProperty("referenceId")

	@Size(min = 2, max = 64)
	private String referenceId = null;

	@JsonProperty("fromPeriod")

	@Valid
	private BigDecimal fromPeriod = null;

	@JsonProperty("toPeriod")

	@Valid
	private BigDecimal toPeriod = null;

	@JsonProperty("payee")

	@Valid
	private Party payee = null;

	@JsonProperty("lineItems")
	@Valid
	private List<LineItem> lineItems = null;

	@JsonProperty("payableLineItem")
	@NotNull
	@Valid
	private List<LineItem> payableLineItem = new ArrayList<>();

	public CalcDetail addLineItemsItem(LineItem lineItemsItem) {
		if (this.lineItems == null) {
			this.lineItems = new ArrayList<>();
		}
		this.lineItems.add(lineItemsItem);
		return this;
	}

	public CalcDetail addPayableLineItemItem(LineItem payableLineItemItem) {
		this.payableLineItem.add(payableLineItemItem);
		return this;
	}

}

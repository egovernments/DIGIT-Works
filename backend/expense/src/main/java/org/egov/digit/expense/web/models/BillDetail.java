package org.egov.digit.expense.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
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
 * Bill details of the individual payees
 */
@Schema(description = "Bill details of the individual payees")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetail {

	@JsonProperty("id")
	@Valid
	private UUID id;

	@JsonProperty("referenceId")
	@Size(min = 2, max = 64)
	private String referenceId;

	@JsonProperty("paymentStatus")
	@Size(min = 2, max = 64)
	private String paymentStatus;

	@JsonProperty("fromPeriod")
	@Valid
	private BigDecimal fromPeriod;

	@JsonProperty("toPeriod")
	@Valid
	private BigDecimal toPeriod;

	@JsonProperty("payee")
	@NotNull
	@Valid
	private Party payee;

	@JsonProperty("lineItems")
	@Valid
	private List<LineItems> lineItems;

	@JsonProperty("payableLineItem")
	@NotNull
	@Valid
	private List<LineItems> payableLineItem;

	public BillDetail addLineItemsItem(LineItems lineItemsItem) {
		if (this.lineItems == null) {
			this.lineItems = new ArrayList<>();
		}
		this.lineItems.add(lineItemsItem);
		return this;
	}

	public BillDetail addPayableLineItemItem(LineItems payableLineItemItem) {
		this.payableLineItem.add(payableLineItemItem);
		return this;
	}

}

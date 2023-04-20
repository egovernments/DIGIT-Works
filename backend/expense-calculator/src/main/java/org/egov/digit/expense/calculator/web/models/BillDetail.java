package org.egov.digit.expense.calculator.web.models;

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
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetail {
	@JsonProperty("id")
	@Valid
	private String id = null;

	@JsonProperty("tenantId")
	private String tenantId = null;

	@JsonProperty("referenceId")
	@Size(min = 2, max = 64)
	private String referenceId = null;

	@JsonProperty("paymentStatus")

	@Size(min = 2, max = 64)
	private String paymentStatus = null;

	@JsonProperty("fromPeriod")

	@Valid
	private BigDecimal fromPeriod = null;

	@JsonProperty("toPeriod")

	@Valid
	private BigDecimal toPeriod = null;

	@JsonProperty("netLineItemAmount")

	@Valid
	private BigDecimal netLineItemAmount = null;

	@JsonProperty("payee")
	@NotNull

	@Valid
	private Party payee = null;

	@JsonProperty("lineItems")
	@Valid
	private List<LineItem> lineItems = null;

	@JsonProperty("payableLineItems")
	@Valid
	private List<LineItem> payableLineItems = null;

	@JsonProperty("additionalDetails")

	private Object additionalDetails = null;

	@JsonProperty("auditDetails")

	@Valid
	private AuditDetails auditDetails = null;

	public BillDetail addLineItemsItem(LineItem lineItemsItem) {
		if (this.lineItems == null) {
			this.lineItems = new ArrayList<>();
		}
		this.lineItems.add(lineItemsItem);
		return this;
	}

	public BillDetail addPayableLineItemsItem(LineItem payableLineItemsItem) {
		if (this.payableLineItems == null) {
			this.payableLineItems = new ArrayList<>();
		}
		this.payableLineItems.add(payableLineItemsItem);
		return this;
	}

}

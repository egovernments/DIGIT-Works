package org.egov.digit.expense.calculator.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import digit.models.coremodels.AuditDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bill details of the individual payee
 */
@Schema(description = "Bill details of the individual payees")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetail {

	@JsonProperty("id")
	@Valid
	private String id;

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;

	@JsonProperty("billId")
	@Valid
	private String billId;

	@JsonProperty("totalAmount")
	@Valid
	@Default
	private BigDecimal totalAmount = BigDecimal.ZERO;;

	@JsonProperty("totalPaidAmount")
	@Valid
	@Default
	private BigDecimal totalPaidAmount = BigDecimal.ZERO;

	@JsonProperty("netLineItemAmount")
	@Valid
	@Default
	private BigDecimal netLineItemAmount = BigDecimal.ZERO;

	@JsonProperty("referenceId")
	@Size(min = 2, max = 64)
	private String referenceId;

	@JsonProperty("paymentStatus")
	@Size(min = 2, max = 64)
	private String paymentStatus;

	@JsonProperty("fromPeriod")
	@Valid
	private Long fromPeriod;

	@JsonProperty("toPeriod")
	@Valid
	private Long toPeriod;

	@JsonProperty("payee")
	@NotNull
	@Valid
	private Party payee;

	@JsonProperty("lineItems")
	@Valid
	private List<LineItem> lineItems;

	@JsonProperty("payableLineItems")
	@NotNull
	@Valid
	private List<LineItem> payableLineItems;

	@JsonProperty("auditDetails")
	@Valid
	private AuditDetails auditDetails;

	@JsonProperty("additionalDetails")
	private Object additionalDetails;

	public BillDetail addLineItems(LineItem lineItem) {

		if (this.lineItems == null) {
			this.lineItems = new ArrayList<>();
		}
		this.lineItems.add(lineItem);
		return this;
	}

	public BillDetail addPayableLineItems(LineItem payableLineItem) {

		if (this.payableLineItems == null) {
			this.payableLineItems = new ArrayList<>();
		}
		this.payableLineItems.add(payableLineItem);
		return this;
	}

}

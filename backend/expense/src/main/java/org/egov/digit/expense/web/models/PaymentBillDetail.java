package org.egov.digit.expense.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.enums.PaymentStatus;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bill details of the individual payees
 */
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentBillDetail {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("tenantId")
	private String tenantId;

	@JsonProperty("billDetailId")
	@NotNull
	private String billDetailId;

	@JsonProperty("totalAmount")
	private BigDecimal totalAmount;

	@JsonProperty("totalPaidAmount")
	@Default
	private BigDecimal totalPaidAmount = BigDecimal.ZERO;
	
	@JsonProperty("status")
	private PaymentStatus status;

	@JsonProperty("payableLineItems")
	@Valid
	@NotNull
	private List<PaymentLineItem> payableLineItems;

	@JsonProperty("auditDetails")
	private AuditDetails auditDetails;
	
	public PaymentBillDetail addLineItem(PaymentLineItem paymentLineItem) {

		if (null == this.payableLineItems)
			this.payableLineItems = new ArrayList<>();

		this.payableLineItems.add(paymentLineItem);
		return this;
	}
}

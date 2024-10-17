package org.egov.digit.expense.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.enums.PaymentStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Object which holds the info about the expense details
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentBill {
	
	@JsonProperty("id")
	private String id;

	@JsonProperty("billId")
	@NotNull
	private String billId;

	@JsonProperty("tenantId")
	private String tenantId;

	@JsonProperty("totalAmount")
	private BigDecimal totalAmount;

	@JsonProperty("totalPaidAmount")
	@Default
	private BigDecimal totalPaidAmount = BigDecimal.ZERO;
	
	@JsonProperty("status")
	private PaymentStatus status;

	@JsonProperty("billDetails")
	@Valid
	@NotNull
	private List<PaymentBillDetail> billDetails;
	
  @JsonProperty("auditDetails")
	private AuditDetails auditDetails;
	
	public PaymentBill addPaymentBillDetailItem (PaymentBillDetail paymentBillDetailItem) {

		if (null == this.billDetails)
			this.billDetails = new ArrayList<>();

		this.billDetails.add(paymentBillDetailItem);
		return this;
	}
}

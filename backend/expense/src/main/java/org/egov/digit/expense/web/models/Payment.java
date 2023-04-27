package org.egov.digit.expense.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payment
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
	
	@JsonProperty("tenantId")
	private String tenantId;

	@JsonProperty("id")
	@Valid
	private String id;

	@JsonProperty("netPayableAmount")
	private BigDecimal netPayableAmount;

	@JsonProperty("netPaidAmount")
	private BigDecimal netPaidAmount;

	@JsonProperty("bills")
	@Valid
	private List<PaymentBill> bills;

	@JsonProperty("status")
	private String status;
	
	@JsonProperty("auditDetails")
	private AuditDetails auditDetails;
	
	@JsonProperty("additionalDetails")
	private Object additionalDetails;
	
	public Payment addBillDetailsItem(PaymentBill bill) {

		if (this.bills == null)
			this.bills = new ArrayList<>();
		this.bills.add(bill);
		return this;
	}
}

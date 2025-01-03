package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.egov.works.services.common.models.expense.enums.PaymentStatus;
import org.egov.works.services.common.models.expense.enums.ReferenceStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Payment
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
	
	@JsonProperty("tenantId")
	@NotNull
	private String tenantId;

	@JsonProperty("id")
	@Valid
	private String id;

	@JsonProperty("netPayableAmount")
	@NotNull
	private BigDecimal netPayableAmount;

	@JsonProperty("netPaidAmount")
	@NotNull
	private BigDecimal netPaidAmount;

	@JsonProperty("bills")
	@Valid
	@NotNull
	private List<PaymentBill> bills;
	
	@JsonProperty("paymentNumber")
	private String paymentNumber;

	@JsonProperty("status")
	private PaymentStatus status;

	@JsonProperty("referenceStatus")
	private ReferenceStatus referenceStatus;
	
	@JsonProperty("auditDetails")
	private AuditDetails auditDetails;
	
	@JsonProperty("additionalDetails")
	private Object additionalDetails;
	
	public Payment addBillItem(PaymentBill bill) {

		if (this.bills == null)
			this.bills = new ArrayList<>();
		this.bills.add(bill);
		return this;
	}
}
package org.egov.web.models.bill;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.web.models.enums.PaymentStatus;
import org.egov.web.models.enums.ReferenceStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

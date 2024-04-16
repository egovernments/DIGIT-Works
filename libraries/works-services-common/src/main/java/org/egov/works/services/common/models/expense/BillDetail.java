package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.egov.works.services.common.models.expense.enums.PaymentStatus;
import org.egov.works.services.common.models.expense.enums.Status;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
	@Builder.Default
	private BigDecimal totalAmount = BigDecimal.ZERO;;
	
	@JsonProperty("totalPaidAmount")
	@Valid
	@Builder.Default
	private BigDecimal totalPaidAmount = BigDecimal.ZERO;
	
	@JsonProperty("referenceId")
	@Size(min = 2, max = 64)
	private String referenceId;

	@JsonProperty("paymentStatus")
	private PaymentStatus paymentStatus;
	
	@JsonProperty("status")
	private Status status;

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

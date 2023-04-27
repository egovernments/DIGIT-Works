package org.egov.digit.expense.web.models;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

	@JsonProperty("billDetailId")
	private String billDetailId;

	@JsonProperty("netLineItemAmount")
	private BigDecimal netLineItemAmount;

	@JsonProperty("payableLineItems")
	@Valid
	private List<PaymentLineItem> payableLineItems;

	@JsonProperty("auditDetails")
	private AuditDetails auditDetails;
}

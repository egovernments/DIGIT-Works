package org.egov.digit.expense.web.models;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
	private String billId;

	@JsonProperty("tenantId")
	private String tenantId;

	@JsonProperty("billAmount")
	private BigDecimal billAmount;

	@JsonProperty("paidAmount")
	private BigDecimal paidAmount;

	@JsonProperty("billDetails")
	@Valid
	private List<PaymentBillDetail> billDetails;
}

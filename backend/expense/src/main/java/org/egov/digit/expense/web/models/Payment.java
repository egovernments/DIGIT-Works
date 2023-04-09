package org.egov.digit.expense.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payment
 */
@Schema(description = "A Object which holds the info about the expense details")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
	
	@JsonProperty("tenantId")
	private String tenantId;

	@JsonProperty("id")
	@Valid
	private List<String> id;

	@JsonProperty("netPayableAmount")
	private BigDecimal netPayableAmount;

	@JsonProperty("netPaidAmount")
	private BigDecimal netPaidAmount;

	@JsonProperty("bills")
	@Valid
	private List<Bill> bills;

	@JsonProperty("status")
	private String status;
	
	public Payment addBillDetailsItem(Bill bill) {

		if (this.bills == null)
			this.bills = new ArrayList<>();
		this.bills.add(bill);
		return this;
	}
}

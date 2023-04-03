package org.egov.digit.expense.web.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillResponse {

	@JsonProperty("responseInfo")
	@Valid
	private ResponseInfo responseInfo;

	@JsonProperty("bill")
	@Valid
	private List<Bill> bill;

	@JsonProperty("pagination")
	@Valid
	private Pagination pagination;

	public BillResponse addBillItem(Bill billItem) {
		if (this.bill == null) {
			this.bill = new ArrayList<>();
		}
		this.bill.add(billItem);
		return this;
	}

}

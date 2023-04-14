package org.egov.digit.expense.calculator.web.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
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
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillResponse {
	@JsonProperty("responseInfo")

	@Valid
	private ResponseInfo responseInfo = null;

	@JsonProperty("bill")
	@Valid
	private List<Bill> bill = null;

	@JsonProperty("pagination")

	@Valid
	private Pagination pagination = null;

	public BillResponse addBillItem(Bill billItem) {
		if (this.bill == null) {
			this.bill = new ArrayList<>();
		}
		this.bill.add(billItem);
		return this;
	}

}

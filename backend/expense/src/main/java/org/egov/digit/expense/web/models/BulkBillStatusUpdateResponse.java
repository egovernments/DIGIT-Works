package org.egov.digit.expense.web.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BulkBillStatusUpdateResponse
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulkBillStatusUpdateResponse {

	@JsonProperty("ResponseInfo")
	@Valid
	private ResponseInfo responseInfo;

	@JsonProperty("bills")
	@Valid
	private List<Bill> bills;

	@JsonProperty("errors")
	@Valid
	private List<BulkUpdateError> errors;

	public BulkBillStatusUpdateResponse addBillItem(Bill billItem) {
		if (this.bills == null) {
			this.bills = new ArrayList<>();
		}
		this.bills.add(billItem);
		return this;
	}

	public BulkBillStatusUpdateResponse addError(BulkUpdateError error) {
		if (this.errors == null) {
			this.errors = new ArrayList<>();
		}
		this.errors.add(error);
		return this;
	}

}
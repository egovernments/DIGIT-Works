package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BulkUpdateError
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulkUpdateError {

	@JsonProperty("billId")
	private String billId;

	@JsonProperty("code")
	private String code;

	@JsonProperty("message")
	private String message;

}
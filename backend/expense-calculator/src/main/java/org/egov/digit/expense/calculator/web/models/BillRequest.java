package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

/**
 * BillRequest
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BillRequest {

	@JsonProperty("RequestInfo")
	@Valid
	private RequestInfo requestInfo;

	@JsonProperty("bill")
	@Valid
	private Bill bill;

	@JsonProperty("workflow")
	private Workflow workflow;

}

package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;

import jakarta.validation.Valid;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

package org.egov.digit.expense.web.models;

import java.util.List;

import jakarta.validation.Valid;

import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BulkBillStatusUpdateRequest
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulkBillStatusUpdateRequest {

	@JsonProperty("RequestInfo")
	@Valid
	private RequestInfo requestInfo;

	@JsonProperty("billIds")
	@Valid
	private List<String> billIds;

	@JsonProperty("status")
	private String status;

	@JsonProperty("workflow")
	private Workflow workflow;

}
package org.egov.digit.expense.web.models;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;

	@JsonProperty("billIds")
	@Valid
	private List<String> billIds;

	@JsonProperty("status")
	private String status;

	@JsonProperty("workflow")
	private Workflow workflow;

	/**
	 * Sign-off applied to every bill in this bulk transition: printed name + signature
	 * image (filestore id) of the acting payment editor / reviewer / approver.
	 */
	@JsonProperty("signature")
	@Valid
	private BillSignature signature;

}
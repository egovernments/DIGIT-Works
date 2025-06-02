package org.egov.digit.expense.web.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.egov.common.contract.models.AuditDetails;

import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bill verification task details of the individual payee
 */
@Schema(description = "Bill verification task details of the individual payees")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2025-05-20T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillVerificationTaskDetails {
	
	@JsonProperty("id")
	@Valid
	private String id;

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;
	
	@JsonProperty("billId")
	@Valid
	private String billId;
	
	@JsonProperty("payeeId")
	@NotNull
	@Valid
	private String payeeId;

	@JsonProperty("taskId")
	private String taskId;

	@JsonProperty("referenceId")
	@Size(min = 2, max = 64)
	private String referenceId;

	@JsonProperty("status")
	private String responseMessage;
	
	@JsonProperty("status")
	private Status status;

	@JsonProperty("status")
	private String reasonForFailure;

	@JsonProperty("auditDetails")
	@Valid
	private AuditDetails auditDetails;
	
	@JsonProperty("additionalDetails")
	private Object additionalDetails;
	
}

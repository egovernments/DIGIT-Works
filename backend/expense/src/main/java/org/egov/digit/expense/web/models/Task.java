package org.egov.digit.expense.web.models;

import jakarta.validation.Valid;

import org.egov.common.contract.models.AuditDetails;

import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "task for a bill and payments")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2025-05-20T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

	@JsonProperty("id")
	@Valid
	private String id;

	@JsonProperty("billId")
	private String billId;
	
	@JsonProperty("status")
	private Status status;

	@JsonProperty("type")
	private Type type;

	@JsonProperty("auditDetails")
	@Valid
	private AuditDetails auditDetails;
	
	@JsonProperty("additionalDetails")
	private Object additionalDetails;

	public enum Type {
		Verify,
		Transfer
	}
}

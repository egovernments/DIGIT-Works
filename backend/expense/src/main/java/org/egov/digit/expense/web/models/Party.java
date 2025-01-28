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
 * Account details
 */
@Schema(description = "Account details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Party {

	@JsonProperty("id")
	@Valid
	private String id;

	@JsonProperty("parentId")
	@Valid
	private String parentId;

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;
	
	@JsonProperty("type")
	@NotNull
	@Size(min = 2, max = 64)
	private String type;

	@JsonProperty("identifier")
	@NotNull
	@Size(min = 2, max = 64)
	private String identifier;

	@JsonProperty("status")
	private Status status;

	@JsonProperty("additionalDetails")
	private Object additionalDetails;

	@JsonProperty("auditDetails")
	@Valid
	private AuditDetails auditDetails;

}





package org.egov.digit.expense.calculator.web.models;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Collection of audit related fields used by most models
 */
@Schema(description = "Collection of audit related fields used by most models")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDetails {
	@JsonProperty("createdBy")

	private String createdBy = null;

	@JsonProperty("lastModifiedBy")

	private String lastModifiedBy = null;

	@JsonProperty("createdTime")

	private Long createdTime = null;

	@JsonProperty("lastModifiedTime")

	private Long lastModifiedTime = null;

}

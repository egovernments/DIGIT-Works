package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * CalculatorSearchCriteria
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculatorSearchCriteria {
	
	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;

	@JsonProperty("projectNumber")
	private String projectNumber;

	@JsonProperty("contractNumber")
	private String contractNumber;

	@JsonProperty("orgNumber")
	private String orgNumber;

	@JsonProperty("musterRollNumber")
	private String musterRollNumber;

	@JsonProperty("billNumber")
	private String billNumber;

	@JsonProperty("billType")
	private String billType;

	@JsonProperty("billRefId")
	private String billReferenceId;

}


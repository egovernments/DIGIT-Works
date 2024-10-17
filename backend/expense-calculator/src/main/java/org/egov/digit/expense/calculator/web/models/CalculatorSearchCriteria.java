package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * CalculatorSearchCriteria
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculatorSearchCriteria {
	
	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;

	@JsonProperty("projectNumbers")
	private List<String> projectNumbers;

	@JsonProperty("contractNumbers")
	private List<String> contractNumbers;

	@JsonProperty("orgNumbers")
	private List<String> orgNumbers;

	@JsonProperty("musterRollNumbers")
	private List<String> musterRollNumbers;

	@JsonProperty("billNumbers")
	private List<String> billNumbers;

	@JsonProperty("billTypes")
	private List<String> billTypes;

	@JsonProperty("billRefIds")
	private List<String> billReferenceIds;
	
	@JsonProperty("boundaryType")
	private String boundaryType;
	
	@JsonProperty("boundary")
	private String boundary;
	
	@JsonProperty("projectName")
	private String projectName;

}


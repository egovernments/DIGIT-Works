package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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


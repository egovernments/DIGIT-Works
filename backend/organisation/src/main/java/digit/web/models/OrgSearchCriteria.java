package digit.web.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * organisation search attributes
 */
@ApiModel(description = "organisation search attributes")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgSearchCriteria {
	@JsonProperty("id")

	private List<String> id = null;

	@JsonProperty("tenantId")

	@Size(min = 2, max = 1000)

	private String tenantId = null;

	@JsonProperty("type")

	private String type = null;

	@JsonProperty("functions")

	private Object functions = null;

	@JsonProperty("identifierType")

	private String identifierType = null;

	@JsonProperty("identifierValue")

	private String identifierValue = null;

	@JsonProperty("includeDeleted")

	private Boolean includeDeleted = false;

	public OrgSearchCriteria addIdItem(String idItem) {
		if (this.id == null) {
			this.id = new ArrayList<>();
		}
		this.id.add(idItem);
		return this;
	}

}

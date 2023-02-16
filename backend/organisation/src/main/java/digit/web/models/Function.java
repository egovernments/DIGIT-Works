package digit.web.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import digit.models.coremodels.Document;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the functions of an organisation
 */
@ApiModel(description = "Represents the functions of an organisation")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Function {
	@JsonProperty("orgId")

	private String orgId = null;

	@JsonProperty("applicationNumber")

	private String applicationNumber = null;

	@JsonProperty("type")

	@Size(min = 2, max = 64)

	private String type = null;

	@JsonProperty("category")

	@Size(min = 2, max = 64)

	private String category = null;

	@JsonProperty("class")

	@Size(min = 2, max = 64)

	private String propertyClass = null;

	@JsonProperty("validFrom")

	private Double validFrom = null;

	@JsonProperty("validTo")

	private Double validTo = null;

	@JsonProperty("applicationStatus")

	@Size(min = 2, max = 64)

	private String applicationStatus = null;

	@JsonProperty("isActive")

	private Boolean isActive = null;

	@JsonProperty("documents")

	@Valid

	private List<Document> documents = null;


    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;
	
    @JsonProperty("auditDetails")

	private Object auditDetails = null;

	public Function addDocumentsItem(Document documentsItem) {
		if (this.documents == null) {
			this.documents = new ArrayList<>();
		}
		this.documents.add(documentsItem);
		return this;
	}

}

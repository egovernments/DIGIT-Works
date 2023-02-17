package digit.web.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrgServiceResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgServiceResponse {
	@JsonProperty("ResponseInfo")

	private Object responseInfo = null;

	@JsonProperty("organisations")

	@Valid

	private List<Organisation> organisations = null;

	@JsonProperty("pagination")

	private Object pagination = null;

	@JsonProperty("workflow")

	@Valid

	private Workflow workflow = null;

	public OrgServiceResponse addOrganisationsItem(Organisation organisationsItem) {
		if (this.organisations == null) {
			this.organisations = new ArrayList<>();
		}
		this.organisations.add(organisationsItem);
		return this;
	}

}

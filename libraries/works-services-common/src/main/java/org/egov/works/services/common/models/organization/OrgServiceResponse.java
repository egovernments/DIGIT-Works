package org.egov.works.services.common.models.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgServiceResponse {

	@JsonProperty("ResponseInfo")
	private ResponseInfo responseInfo = null;

	@JsonProperty("organisations")
	@Valid
	private List<Organisation> organisations = null;

	@JsonProperty("pagination")
	private Pagination pagination = null;

	@JsonProperty("workflow")
	@Valid
	private Workflow workflow = null;

	@JsonProperty("TotalCount")
	private Integer totalCount = 0;

	public OrgServiceResponse addOrganisationsItem(Organisation organisationsItem) {
		if (this.organisations == null) {
			this.organisations = new ArrayList<>();
		}
		this.organisations.add(organisationsItem);
		return this;
	}

}

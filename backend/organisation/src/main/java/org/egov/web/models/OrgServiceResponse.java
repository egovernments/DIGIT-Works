package org.egov.web.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.response.ResponseInfo;
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
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

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

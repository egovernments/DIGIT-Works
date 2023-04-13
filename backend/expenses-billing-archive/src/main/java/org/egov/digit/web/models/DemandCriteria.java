package org.egov.digit.web.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DemandCriteria
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-15T12:39:54.253+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandCriteria {
	@JsonProperty("tenantId")
	@NotNull

	@Size(min = 2, max = 64)
	private String tenantId = null;

	@JsonProperty("ids")

	private List<String> ids = null;

	@JsonProperty("businessService")

	@Size(min = 2, max = 64)
	private String businessService = null;

	@JsonProperty("referenceId")

	private List<String> referenceId = null;

	@JsonProperty("isActive")

	private Boolean isActive = null;

	public DemandCriteria addIdsItem(String idsItem) {
		if (this.ids == null) {
			this.ids = new ArrayList<>();
		}
		this.ids.add(idsItem);
		return this;
	}

	public DemandCriteria addReferenceIdItem(String referenceIdItem) {
		if (this.referenceId == null) {
			this.referenceId = new ArrayList<>();
		}
		this.referenceId.add(referenceIdItem);
		return this;
	}

}

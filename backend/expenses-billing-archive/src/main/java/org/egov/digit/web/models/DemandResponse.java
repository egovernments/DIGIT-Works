package org.egov.digit.web.models;

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
 * DemandResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-15T12:39:54.253+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandResponse {
	@JsonProperty("responseInfo")

	@Valid
	private ResponseInfo responseInfo = null;

	@JsonProperty("demands")
	@Valid
	private List<Demand> demands = null;

	@JsonProperty("pagination")

	@Valid
	private Pagination pagination = null;

	public DemandResponse addDemandsItem(Demand demandsItem) {
		if (this.demands == null) {
			this.demands = new ArrayList<>();
		}
		this.demands.add(demandsItem);
		return this;
	}

}

package org.egov.web.models.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.models.core.Boundary;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoundaryResponse {

	@JsonProperty("ResponseInfo")
	@Valid
	private ResponseInfo responseInfo = null;

	@JsonProperty("Boundary")
	@Valid
	private List<Boundary> boundary = null;


	public BoundaryResponse addBoundaryItem(Boundary boundaryItem) {
		if (this.boundary == null) {
			this.boundary = new ArrayList<>();
		}
		this.boundary.add(boundaryItem);
		return this;
	}

}

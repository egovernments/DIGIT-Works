package org.egov.works.services.common.models.musterroll;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egov.common.contract.request.RequestInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusterRoleSearchRequest {

	@JsonProperty("RequestInfo")
	@Valid
    	private RequestInfo requestInfo = null;
	
	@JsonProperty("musterRoll")
	@NotNull(message = "Muster Roll is mandatory")
	@Valid
	private MusterRollSearchCriteria musterRoll = null;
}

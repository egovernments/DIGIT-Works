package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egov.common.contract.models.RequestInfoWrapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MusterRollSearchRequest extends RequestInfoWrapper {
    @JsonProperty("musterRoll")
    @NotNull(message = "Muster Roll is mandatory")
    @Valid
    private MusterRollSearchCriteria musterRoll = null;
}

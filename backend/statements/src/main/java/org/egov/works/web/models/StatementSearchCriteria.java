package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;


/**
 * StatementSearchCriteria
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatementSearchCriteria {
    @JsonProperty("RequestInfo")

    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("searchCriteria")

    @Valid
    @NotNull
    private SearchCriteria searchCriteria = null;


}

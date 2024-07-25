package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.egov.web.models.Pagination;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Schema(description = "A Object which holds the info about the payment search request")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PISearchRequest {
    @JsonProperty("RequestInfo")
    @NotNull
    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("searchCriteria")
    @Valid
    @NotNull
    private PISearchCriteria searchCriteria = null;

//    @JsonProperty("pagination")
//    @Valid
//    private Pagination pagination = null;
}

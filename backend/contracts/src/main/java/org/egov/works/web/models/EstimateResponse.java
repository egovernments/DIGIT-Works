package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Response for the Estimate _create, _update and _search api&#39;s
 */
@ApiModel(description = "Response for the Estimate _create, _update and _search api's")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateResponse {

    @JsonProperty("responseInfo")
    private ResponseInfo responseInfo = null;
    @JsonProperty("estimates")
    @Valid
    private List<Estimate> estimates = new ArrayList<>();

    public EstimateResponse addEstimatesItem(Estimate estimatesItem) {
        this.estimates.add(estimatesItem);
        return this;
    }

}


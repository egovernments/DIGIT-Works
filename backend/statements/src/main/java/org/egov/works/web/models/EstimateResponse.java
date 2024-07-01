package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;
import org.egov.works.services.common.models.estimate.Estimate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Response for the Estimate _create, _update and _search api&#39;s
 */
@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateResponse {

    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

    @JsonProperty("estimates")
    @Valid
    private List<Estimate> estimates = new ArrayList<>();

    @JsonProperty("TotalCount")
    private Integer totalCount = 0;


    public EstimateResponse addEstimatesItem(Estimate estimatesItem) {
        this.estimates.add(estimatesItem);
        return this;
    }

}


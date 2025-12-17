package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * RateAnalysisResponse
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateAnalysisResponse {
    @JsonProperty("responseInfo")

    @Valid
    private ResponseInfo responseInfo = null;

    @JsonProperty("rateAnalysis")
    @Valid
    private List<RateAnalysis> rateAnalysis = null;


    public RateAnalysisResponse addRateAnalysisItem(RateAnalysis rateAnalysisItem) {
        if (this.rateAnalysis == null) {
            this.rateAnalysis = new ArrayList<>();
        }
        this.rateAnalysis.add(rateAnalysisItem);
        return this;
    }

}

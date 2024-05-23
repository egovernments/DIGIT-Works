package org.egov.works.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.egov.works.web.models.RateAnalysis;
import org.egov.works.web.models.ResponseInfo;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * RateAnalysisResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateAnalysisResponse   {
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

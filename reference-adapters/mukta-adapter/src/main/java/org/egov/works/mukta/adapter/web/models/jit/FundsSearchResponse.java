package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Schema(description = "A Object which holds the info about the payment search request")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundsSearchResponse {

    @JsonProperty("funds")
    List<SanctionDetail> funds = null;
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

}

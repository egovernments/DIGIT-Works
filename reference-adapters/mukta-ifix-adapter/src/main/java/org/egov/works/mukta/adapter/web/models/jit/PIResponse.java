package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PIResponse {

    @JsonProperty("paymentInstructions")
    List<PaymentInstruction> paymentInstructions = null;
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

}

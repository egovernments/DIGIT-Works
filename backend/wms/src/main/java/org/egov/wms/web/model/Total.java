package org.egov.wms.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Total {

    @JsonProperty("estimatedAmount")
    private Double estimatedAmount;

    @JsonProperty("paymentDetails")
    private List<PaymentDetailsByBillType> paymentDetailsByBillType;

}

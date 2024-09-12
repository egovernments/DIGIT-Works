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
public class ProjectPaymentDetails {

    @JsonProperty("projectNumber")
    private String projectNumber;

    @JsonProperty("estimatedAmount")
    private Integer estimatedAmount;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("paymentDetails")
    private List<PaymentDetailsByBillType> paymentDetails;

}

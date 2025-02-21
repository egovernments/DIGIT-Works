package org.egov.wms.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsByBillType {

    @JsonProperty("billType")
    private String billType;

    @JsonProperty("total")
    private Double total;

    @JsonProperty("paidAmount")
    private Double paidAmount;

    @JsonProperty("remainingAmount")
    private Double remainingAmount;

}

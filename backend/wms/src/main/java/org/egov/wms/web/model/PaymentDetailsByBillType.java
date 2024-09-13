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
    private Integer total;

    @JsonProperty("paidAmount")
    private Integer paidAmount;

    @JsonProperty("remainingAmount")
    private Integer remainingAmount;

}

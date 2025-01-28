package org.egov.works.services.common.models.estimate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateDetail {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("previousLineItemId")
    private String previousLineItemId = null;

    @Size(min = 1,max = 64)
    @JsonProperty("sorId")
    private String sorId = null;

    @JsonProperty("category")
    private String category = null;

    @JsonProperty("name")
    @Size(min = 2,max = 256)
    private String name = null;

    @JsonProperty("description")
    @Size(min = 2,max = 256)
    private String description = null;

    @JsonProperty("unitRate")
    private Double unitRate = null;

    @JsonProperty("noOfunit")
    private Double noOfunit = null;

    @JsonProperty("uom")
    private String uom = null;

    @JsonProperty("uomValue")
    private Double uomValue = null;

    @JsonProperty("length")
    private BigDecimal length = null;

    @JsonProperty("width")
    private BigDecimal width = null;

    @JsonProperty("height")
    private BigDecimal height =null;

    @JsonProperty("quantity")
    private BigDecimal quantity =null;

    @JsonProperty("isDeduction")
    private Boolean isDeduction=null;

    @JsonProperty("amountDetail")
    @Valid
    private List<AmountDetail> amountDetail = null;

    @JsonProperty("isActive")
    private boolean isActive = true;

//    @JsonProperty("totalAmount")
//    private Double totalAmount = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


    public EstimateDetail addAmountDetailItem(AmountDetail amountDetailItem) {
        if (this.amountDetail == null) {
            this.amountDetail = new ArrayList<>();
        }
        this.amountDetail.add(amountDetailItem);
        return this;
    }

}


package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * EstimateDetail
 */
@Validated
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

    @JsonProperty("sorId")
    private String sorId = null;

    @JsonProperty("category")
    private String category = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
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
    private List<BasicSorDetails> amountDetail = null;

    @JsonProperty("isActive")
    private boolean isActive = true;

//    @JsonProperty("totalAmount")
//    private Double totalAmount = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


    public EstimateDetail addAmountDetailItem(BasicSorDetails amountDetailItem) {
        if (this.amountDetail == null) {
            this.amountDetail = new ArrayList<>();
        }
        this.amountDetail.add(amountDetailItem);
        return this;
    }

}


package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;


@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SorDetail {
    @JsonProperty("id")

    @Size(min = 2, max = 64)
    private String id = null;

    @JsonProperty("statementId")

    @Size(min = 2, max = 64)
    private String statementId = null;

    @JsonProperty("sorId")

    @Size(min = 2, max = 64)
    private String sorId = null;

    @JsonProperty("basicSorDetails")
    @Valid
    private List<BasicSorDetails> basicSorDetails = null;

    @JsonProperty("lineItems")
    @Valid
    private List<BasicSor> lineItems = null;
    @JsonProperty("tenantId")
    private String tenantId=null;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


    public SorDetail addAmountDetailsItem(BasicSorDetails basicSorDetailsItem) {
        if (this.basicSorDetails == null) {
            this.basicSorDetails = new ArrayList<>();
        }
        this.basicSorDetails.add(basicSorDetailsItem);
        return this;
    }

    public SorDetail addLineItemsItem(BasicSor lineItemsItem) {
        if (this.lineItems == null) {
            this.lineItems = new ArrayList<>();
        }
        this.lineItems.add(lineItemsItem);
        return this;
    }

}

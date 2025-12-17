package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * RateAnalysis
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateAnalysis {
    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("tenantId")

    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("compositionId")
    @Size(min = 2, max = 64)
    private String compositionId = null;

    @JsonProperty("sorCode")

    @Size(min = 2, max = 64)
    private String sorCode = null;

    @JsonProperty("sorId")

    @Size(min = 2, max = 64)
    private String sorId = null;

    @JsonProperty("sorType")

    @Size(min = 2, max = 64)
    private String sorType = null;

    @JsonProperty("sorSubType")

    @Size(min = 2, max = 64)
    private String sorSubType = null;

    @JsonProperty("sorVariant")

    @Size(min = 2, max = 64)
    private String sorVariant = null;

    @JsonProperty("isBasicVariant")

    private Boolean isBasicVariant = null;

    @JsonProperty("uom")

    private String uom = null;

    @JsonProperty("quantity")
    private BigDecimal quantity = null;

    @JsonProperty("description")

    private String description = null;
    @JsonProperty("status")

    private StatusEnum status = null;
    @JsonProperty("effectiveFrom")

    private String effectiveFrom = null;
    @JsonProperty("analysisQuantity")

    @Valid
    private BigDecimal analysisQuantity = null;
    @JsonProperty("lineItems")
    @Valid
    private List<LineItem> lineItems = null;

    public RateAnalysis addLineItemsItem(LineItem lineItemsItem) {
        if (this.lineItems == null) {
            this.lineItems = new ArrayList<>();
        }
        this.lineItems.add(lineItemsItem);
        return this;
    }


    /**
     * Gets or Sets status
     */
    public enum StatusEnum {
        ACTIVE("ACTIVE"),

        INACTIVE("INACTIVE");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }

}

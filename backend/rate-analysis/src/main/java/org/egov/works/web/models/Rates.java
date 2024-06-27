package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * Rates
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Builder
public class Rates {
    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("tenantId")

    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("sorCode")

    @Size(min = 2, max = 64)
    private String sorCode = null;

    @JsonProperty("sorId")

    @Size(min = 2, max = 64)
    private String sorId = null;

    @JsonProperty("compositionId")
    @Size(min = 2, max = 64)
    private String compositionId = null;

    @JsonProperty("description")

    private String description = null;

    @JsonProperty("rate")

    @Valid
    private BigDecimal rate = null;

    @JsonProperty("validFrom")

    private String validFrom = null;

    @JsonProperty("validTo")
    private String validTo = null;

    @JsonProperty("amountDetails")
    @Valid
    private List<AmountDetail> amountDetails = null;


    public Rates addAmountDetailsItem(AmountDetail amountDetailsItem) {
        if (this.amountDetails == null) {
            this.amountDetails = new ArrayList<>();
        }
        this.amountDetails.add(amountDetailsItem);
        return this;
    }

}

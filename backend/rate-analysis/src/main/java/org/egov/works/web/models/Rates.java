package org.egov.works.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.egov.works.web.models.AmountDetail;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * Rates
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rates   {
        @JsonProperty("id")

          @Valid
                private UUID id = null;

        @JsonProperty("tenantId")

        @Size(min=2,max=64)         private String tenantId = null;

        @JsonProperty("sorCode")

        @Size(min=2,max=64)         private String sorCode = null;

        @JsonProperty("sorId")

        @Size(min=2,max=64)         private String sorId = null;

        @JsonProperty("sorType")

        @Size(min=2,max=64)         private String sorType = null;

        @JsonProperty("sorSubType")

        @Size(min=2,max=64)         private String sorSubType = null;

        @JsonProperty("sorVariant")

        @Size(min=2,max=64)         private String sorVariant = null;

        @JsonProperty("isBasicVariant")

                private Boolean isBasicVariant = null;

        @JsonProperty("uom")

                private String uom = null;

        @JsonProperty("quantity")

          @Valid
                private BigDecimal quantity = null;

        @JsonProperty("description")

                private String description = null;

        @JsonProperty("rate")

          @Valid
                private BigDecimal rate = null;

        @JsonProperty("effectiveFrom")

                private String effectiveFrom = null;

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

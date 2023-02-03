package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import digit.web.models.AmountBreakup;
import digit.web.models.AuditDetails;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * Overide the negotiated amounts from line items of the estimate
 */
@ApiModel(description = "Overide the negotiated amounts from line items of the estimate")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LineItems   {
        @JsonProperty("id")
        
  @Valid


        private UUID id = null;

        @JsonProperty("estimateId")
          @NotNull

@Size(min=1,max=64) 

        private String estimateId = null;

        @JsonProperty("estimateLineItemId")
        
@Size(min=1,max=64) 

        private String estimateLineItemId = null;

        @JsonProperty("tenantId")
          @NotNull

@Size(min=2,max=64) 

        private String tenantId = null;

        @JsonProperty("unitRate")
        
  @Valid


        private BigDecimal unitRate = null;

        @JsonProperty("noOfunit")
        
  @Valid


        private BigDecimal noOfunit = null;

        @JsonProperty("amountBreakups")
        
  @Valid


        private List<AmountBreakup> amountBreakups = null;

        @JsonProperty("auditDetails")
        
  @Valid


        private AuditDetails auditDetails = null;

        @JsonProperty("additionalDetails")
        


        private Object additionalDetails = null;


        public LineItems addAmountBreakupsItem(AmountBreakup amountBreakupsItem) {
            if (this.amountBreakups == null) {
            this.amountBreakups = new ArrayList<>();
            }
        this.amountBreakups.add(amountBreakupsItem);
        return this;
        }

}


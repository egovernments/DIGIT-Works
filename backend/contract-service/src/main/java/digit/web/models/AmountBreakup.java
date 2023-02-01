package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * AmountBreakup
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmountBreakup   {
        @JsonProperty("id")
        
  @Valid


        private UUID id = null;

        @JsonProperty("estimateAmountBreakupId")
          @NotNull

  @Valid


        private UUID estimateAmountBreakupId = null;

        @JsonProperty("amount")
          @NotNull

  @Valid


        private BigDecimal amount = null;

        @JsonProperty("additionalDetails")
        


        private Object additionalDetails = null;


}


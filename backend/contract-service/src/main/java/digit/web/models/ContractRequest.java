package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import digit.web.models.Contract;
import digit.web.models.RequestInfo;
import digit.web.models.Workflow;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * ContractRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractRequest   {
        @JsonProperty("requestInfo")
        
  @Valid


        private RequestInfo requestInfo = null;

        @JsonProperty("contract")
        
  @Valid


        private Contract contract = null;

        @JsonProperty("workflow")
        
  @Valid


        private Workflow workflow = null;


}


package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import digit.web.models.Contract;
import digit.web.models.Pagination;
import digit.web.models.ResponseInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * ContractResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractResponse   {
        @JsonProperty("responseInfo")
        
  @Valid


        private ResponseInfo responseInfo = null;

        @JsonProperty("contracts")
        
  @Valid


        private List<Contract> contracts = null;

        @JsonProperty("pagination")
        
  @Valid


        private Pagination pagination = null;


        public ContractResponse addContractsItem(Contract contractsItem) {
            if (this.contracts == null) {
            this.contracts = new ArrayList<>();
            }
        this.contracts.add(contractsItem);
        return this;
        }

}


package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * The object will contain all the search parameters for contract service.
 */
@ApiModel(description = "The object will contain all the search parameters for contract service.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractCriteria   {
        @JsonProperty("tenantId")
          @NotNull

@Size(min=2,max=64) 

        private String tenantId = null;

        @JsonProperty("ids")
        


        private List<String> ids = null;

        @JsonProperty("estimateIds")
        


        private List<String> estimateIds = null;

        @JsonProperty("estimateLineItemIds")
        


        private List<String> estimateLineItemIds = null;


        public ContractCriteria addIdsItem(String idsItem) {
            if (this.ids == null) {
            this.ids = new ArrayList<>();
            }
        this.ids.add(idsItem);
        return this;
        }

        public ContractCriteria addEstimateIdsItem(String estimateIdsItem) {
            if (this.estimateIds == null) {
            this.estimateIds = new ArrayList<>();
            }
        this.estimateIds.add(estimateIdsItem);
        return this;
        }

        public ContractCriteria addEstimateLineItemIdsItem(String estimateLineItemIdsItem) {
            if (this.estimateLineItemIds == null) {
            this.estimateLineItemIds = new ArrayList<>();
            }
        this.estimateLineItemIds.add(estimateLineItemIdsItem);
        return this;
        }

}


package org.egov.digit.expense.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * BillCriteria
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillCriteria   {
        @JsonProperty("tenantId")
          @NotNull

        @Size(min=2,max=64)         private String tenantId = null;

        @JsonProperty("ids")

                private List<String> ids = null;

        @JsonProperty("businessService")

        @Size(min=2,max=64)         private String businessService = null;

        @JsonProperty("referenceId")

                private List<String> referenceId = null;

        @JsonProperty("isActive")

                private Boolean isActive = null;


        public BillCriteria addIdsItem(String idsItem) {
            if (this.ids == null) {
            this.ids = new ArrayList<>();
            }
        this.ids.add(idsItem);
        return this;
        }

        public BillCriteria addReferenceIdItem(String referenceIdItem) {
            if (this.referenceId == null) {
            this.referenceId = new ArrayList<>();
            }
        this.referenceId.add(referenceIdItem);
        return this;
        }

}

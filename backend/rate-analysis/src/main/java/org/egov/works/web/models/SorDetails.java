package org.egov.works.web.models;

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
 * SorDetails
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SorDetails   {
        @JsonProperty("tenantId")
          @NotNull

        @Size(min=2,max=64)         private String tenantId = null;

        @JsonProperty("sorCodes")

                private List<String> sorCodes = null;

        @JsonProperty("sorId")
          @NotNull

                private List<String> sorId = new ArrayList<>();

        @JsonProperty("effectiveFrom")

                private String effectiveFrom = null;


        public SorDetails addSorCodesItem(String sorCodesItem) {
            if (this.sorCodes == null) {
            this.sorCodes = new ArrayList<>();
            }
        this.sorCodes.add(sorCodesItem);
        return this;
        }

        public SorDetails addSorIdItem(String sorIdItem) {
        this.sorId.add(sorIdItem);
        return this;
        }

}

package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * SorDetails
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SorDetails {
    @JsonProperty("tenantId")
    @NotNull

    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("sorCodes")
    private List<String> sorCodes = null;

    @JsonProperty("sorId")
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

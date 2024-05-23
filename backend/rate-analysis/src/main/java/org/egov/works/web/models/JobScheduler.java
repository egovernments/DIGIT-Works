package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * JobScheduler
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobScheduler {
    @JsonProperty("id")

    @Valid
    private UUID id = null;

    @JsonProperty("tenantId")

    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("effectiveFrom")
    @NotNull

    @Valid
    private BigDecimal effectiveFrom = null;

    @JsonProperty("sorIds")

    private List<String> sorIds = null;


    public JobScheduler addSorIdsItem(String sorIdsItem) {
        if (this.sorIds == null) {
            this.sorIds = new ArrayList<>();
        }
        this.sorIds.add(sorIdsItem);
        return this;
    }

}

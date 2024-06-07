package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SorComposition {

    @JsonProperty("compositionId")
    @NotNull
    private String compositionId = null;


    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("sorId")
    @NotNull
    @Size(min = 2, max = 64)
    private String sorId = null;

    @JsonProperty("sorType")
    @NotNull
    private String sorType = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("quantity")
    @NotNull
    private BigDecimal quantity = null;

    @JsonProperty("active")
    private Boolean active = null;

    @JsonProperty("effectiveFrom")
    private String effectiveFrom = null;

    @JsonProperty("effectiveTo")
    private String effectiveTo = null;

    @JsonProperty("basicSorDetails")
    private List<SorCompositionBasicSorDetail> basicSorDetails = null;

    @JsonProperty("additionalCharges")
    private List<AdditionalCharges> additionalCharges = null;

}

package org.egov.works.services.common.models.estimate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.Valid;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmountDetail {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("amount")
    @Valid
    @NotNull
    private Double amount = null;

    @JsonProperty("isActive")
    private boolean isActive = true;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

   
}


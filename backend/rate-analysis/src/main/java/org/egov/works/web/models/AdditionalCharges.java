package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalCharges {

    @JsonProperty("description")
    @NotNull
    private String description = null;

    @JsonProperty("applicableOn")
    @NotNull
    private String applicableOn = null;

    @JsonProperty("calculationType")
    @NotNull
    private AmountDetail.TypeEnum calculationType = null;

    @JsonProperty("figure")
    private BigDecimal figure = null;


}

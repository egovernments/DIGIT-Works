package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicSorDetail {

    @JsonProperty("sorId")
    private String sorId = null;

    @JsonProperty("quantity")
    private BigDecimal quantity = null;

    @JsonProperty("perUnitQty")
    private BigDecimal perUnitQty = null;

}

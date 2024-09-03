package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.math.BigDecimal;


@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabourCharge {

    @JsonProperty("id")
    @Valid
    private Integer id = null;

    @JsonProperty("service")
    @Valid
    private String service = null;

    @JsonProperty("code")
    @Valid
    private String code = null;

    @JsonProperty("gender")
    @Valid
    private String gender = null;

    @JsonProperty("active")
    @Valid
    private Boolean active = null;

    @JsonProperty("amount")
    @Valid
    private Double amount = null;

    @JsonProperty("unit")
    @Valid
    private String unit = null;

    @JsonProperty("effectiveFrom")
    @Valid
    private BigDecimal effectiveFrom = null;

    @JsonProperty("effectiveTo")
    @Valid
    private BigDecimal effectiveTo = null;

}

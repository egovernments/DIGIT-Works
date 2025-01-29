package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeadCode {
    @JsonProperty("id")
    private Integer id = null;

    @JsonProperty("code")
    private String code = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("service")
    private String service = null;

    @JsonProperty("category")
    private String category = null;

    @JsonProperty("active")
    private Boolean active = null;

    @JsonProperty("effectiveFrom")
    private BigDecimal effectiveFrom = null;

    @JsonProperty("effectiveTo")
    private BigDecimal effectiveTo = null;
}

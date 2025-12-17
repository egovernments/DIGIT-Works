package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/**
 * AmountDetail
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmountDetail {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("amount")
    private Double amount = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;
}


package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * EstimateDetail
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-07-27T14:01:35.043+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateDetail {

    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("estimateDetailNumber")
    private String estimateDetailNumber = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("amount")
    private BigDecimal amount = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;
}


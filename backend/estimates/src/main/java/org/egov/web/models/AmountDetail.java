package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * AmountDetail
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

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


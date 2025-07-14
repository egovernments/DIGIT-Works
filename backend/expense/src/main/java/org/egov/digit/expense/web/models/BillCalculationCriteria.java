package org.egov.digit.expense.web.models;

import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillCalculationCriteria
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillCalculationCriteria {

    @JsonProperty("tenantId")
    @NotBlank
    private String tenantId;

    @JsonProperty("localityCode")
    @NotBlank
    private String localityCode;

    @JsonProperty("referenceId")
    @NotBlank
    private String referenceId;

}

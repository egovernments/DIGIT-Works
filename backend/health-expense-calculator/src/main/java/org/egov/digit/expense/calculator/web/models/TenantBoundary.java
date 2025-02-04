package org.egov.digit.expense.calculator.web.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantBoundary {

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("hierarchyType")
    private String hierarchyType = null;

    @JsonProperty("boundary")
    private List<Boundary> boundary = null;

}

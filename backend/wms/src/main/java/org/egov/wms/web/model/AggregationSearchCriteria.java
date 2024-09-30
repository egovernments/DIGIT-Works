package org.egov.wms.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregationSearchCriteria{

    @JsonProperty("tenantId")
    @NotNull
    private String tenantId;
    
    @JsonProperty("moduleSearchCriteria")
    private HashMap<String,Object> moduleSearchCriteria;
    
    @JsonProperty("after_key")
    @NotNull
    private String afterKey;

    @JsonProperty("limit")
    @Max(value = 300)
    @NotNull
    private Integer limit;
}

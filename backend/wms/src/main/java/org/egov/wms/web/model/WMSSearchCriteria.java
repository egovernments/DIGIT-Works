package org.egov.wms.web.model;

import java.util.HashMap;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.egov.wms.web.model.workflow.ProcessInstanceSearchCriteria;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WMSSearchCriteria {


    @NotNull
    @JsonProperty("tenantId")
    private String tenantId;

    @Valid
    @JsonProperty("processSearchCriteria")
    private ProcessInstanceSearchCriteria processSearchCriteria;
    
    @JsonProperty("moduleSearchCriteria")
    private HashMap<String,Object> moduleSearchCriteria;
    
    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("limit")
    @Max(value = 300)
    private Integer limit;
}

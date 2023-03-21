package org.egov.wms.web.model;

import java.util.HashMap;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.egov.wms.web.model.workflow.ProcessInstanceSearchCriteria;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
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

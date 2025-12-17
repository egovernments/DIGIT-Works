package org.egov.works.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

@ApiModel(description = "Organisation Jurisdiction")
@Validated

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Jurisdiction {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("orgId")
    private String orgId = null;

    @JsonProperty("code")
    private String code = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;
}
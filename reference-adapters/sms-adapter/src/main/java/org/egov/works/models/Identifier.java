package org.egov.works.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

/**
 * Object to capture tax identifiers for a organisation
 */
@ApiModel(description = "Object to capture tax identifiers for a organisation")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Identifier {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("orgId")
    private String orgId = null;

    @JsonProperty("type")
    @Size(min = 2, max = 64)
    private String type = null;

    @JsonProperty("value")
    @Size(min = 2, max = 64)
    private String value = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("isActive")
    private Boolean isActive = null;

}

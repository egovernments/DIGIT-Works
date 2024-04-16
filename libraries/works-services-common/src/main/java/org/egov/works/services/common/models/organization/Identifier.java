package org.egov.works.services.common.models.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;


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

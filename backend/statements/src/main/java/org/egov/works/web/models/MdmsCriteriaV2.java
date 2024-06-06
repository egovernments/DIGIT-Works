package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MdmsCriteriaV2 {
    @JsonProperty("tenantId")
    @Size(min = 1, max = 100)
    @NotNull
    private String tenantId = null;

    @JsonProperty("ids")
    private Set<String> ids = null;

    @JsonProperty("uniqueIdentifier")
    @Size(min = 1, max = 64)
    private String uniqueIdentifier = null;

    @JsonProperty("schemaCode")
    private String schemaCode = null;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("limit")
    private Integer limit;

}

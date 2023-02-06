package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * TaskResource
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResource {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("productVariantId")
    private String productVariantId = null;

    @JsonProperty("quantity")
    private String quantity = null;

    @JsonProperty("isDelivered")
    private Boolean isDelivered = null;

    @JsonProperty("reasonIfNotDelivered")
    private String reasonIfNotDelivered = null;

    @JsonProperty("isDeleted")
    private Boolean isDeleted = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;


}


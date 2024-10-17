package org.egov.works.services.common.models.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffPermission {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("registerId")
    private String registerId = null;

    @JsonProperty("userId")
    private String userId = null;

    @JsonProperty("enrollmentDate")
    private BigDecimal enrollmentDate = null;

    @JsonProperty("denrollmentDate")
    private BigDecimal denrollmentDate = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


}


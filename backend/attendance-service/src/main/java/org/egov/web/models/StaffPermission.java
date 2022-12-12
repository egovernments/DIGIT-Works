package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * StaffPermission
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T14:44:21.051+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffPermission {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("registerId")
    private String registerId = null;

    @JsonProperty("userId")
    private String userId = null;

    @JsonProperty("enrollmentDate")
    private Double enrollmentDate = null;

    @JsonProperty("denrollmentDate")
    private Double denrollmentDate = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


}


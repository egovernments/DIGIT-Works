package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaceAuthEvent {

    @JsonProperty("id")
    private String id;

    @JsonProperty("clientReferenceId")
    private String clientReferenceId;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("individualId")
    private String individualId;

    @JsonProperty("deviceId")
    private String deviceId;

    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("outcome")
    private String outcome;

    @JsonProperty("confidence")
    private BigDecimal confidence;

    @JsonProperty("latitude")
    private BigDecimal latitude;

    @JsonProperty("longitude")
    private BigDecimal longitude;

    @JsonProperty("locationAccuracy")
    private BigDecimal locationAccuracy;

    @JsonProperty("timestamp")
    private BigDecimal timestamp;

    @JsonProperty("failedAttemptCount")
    private Integer failedAttemptCount;

    @JsonProperty("popupTime")
    private BigDecimal popupTime;

    @JsonProperty("responseTime")
    private BigDecimal responseTime;

    @JsonProperty("responseType")
    private String responseType;

    @JsonProperty("faceImage")
    private String faceImage;

    @JsonProperty("anomalyFlags")
    private String anomalyFlags;

    @JsonProperty("projectId")
    private String projectId;

    @JsonProperty("boundaryCode")
    private String boundaryCode;

    @JsonProperty("additionalDetails")
    private Object additionalDetails;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;

    @JsonProperty("clientAuditDetails")
    private AuditDetails clientAuditDetails;
}

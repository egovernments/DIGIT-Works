package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ScheduledJob
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduledJob {
    @JsonProperty("id")

    @Valid
    private UUID id = null;

    @JsonProperty("tenantId")

    private String tenantId = null;

    @JsonProperty("jobId")

    private String jobId = null;

    @JsonProperty("scheduledOn")

    private String scheduledOn = null;

    @JsonProperty("rateEffectiveFrom")

    private String rateEffectiveFrom = null;

    @JsonProperty("noOfSorScheduled")

    @Valid
    private BigDecimal noOfSorScheduled = null;

    @JsonProperty("auditDetails")

    @Valid
    private AuditDetails auditDetails = null;
    @JsonProperty("status")

    private StatusEnum status = null;
    @JsonProperty("sorDetails")
    @Valid
    private List<SorDetail> sorDetails = null;

    public ScheduledJob addSorDetailsItem(SorDetail sorDetailsItem) {
        if (this.sorDetails == null) {
            this.sorDetails = new ArrayList<>();
        }
        this.sorDetails.add(sorDetailsItem);
        return this;
    }


    /**
     * Overall Status of the JOB
     */
    public enum StatusEnum {
        IN_PROGRESS("IN PROGRESS"),

        PARTIAL("PARTIAL"),

        FAILED("FAILED"),

        COMPLETED("COMPLETED");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }

}

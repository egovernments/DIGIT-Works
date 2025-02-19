package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

/**
 * Model class for triggering report generation. Contains information required
 * to generate reports including tenant and bill details.
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportGenerationTrigger {

    @JsonProperty("RequestInfo")
    @NotNull(message="RequestInfo must not be null")
    /** Contains user and request metadata */
    private RequestInfo requestInfo;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64, message="Tenant ID must be between 2 and 64 characters")
    /** Unique identifier of the tenant */
    private String tenantId;

    @JsonProperty("billId")
    /** Unique identifier of the bill for which report is to be generated */
    private String billId;

    @JsonProperty("createdTime")
    @Max(value = System.currentTimeMillis(), message = "Created time cannot be in the future")
    /** Timestamp when the trigger was created */
    private Long createdTime;

    @JsonProperty("numberOfBillDetails")
    @Min(value = 1, message = "Number of bill details must be positive")
    /** Count of bill details associated with this report */
    private Integer numberOfBillDetails;

}
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

@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportGenerationTrigger {

    @JsonProperty("RequestInfo")
    @NotNull(message="RequestInfo must not be null")
    private RequestInfo requestInfo;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64, message="Tenant ID must be between 2 and 64 characters")
    private String tenantId;

    @JsonProperty("billId")
    private String billId;

    @JsonProperty("createdTime")
    private Long createdTime;

    @JsonProperty("numberOfBillDetails")
    private Integer numberOfBillDetails;


}
package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sanction extends ExchangeCode {
    @JsonProperty("id")
    private String id;
    @JsonProperty("location_code")
    @NotNull
    private String locationCode;
    @JsonProperty("program_code")
    @NotNull
    private String programCode;
    @JsonProperty("sanctioned_amount")
    @NotNull
    private BigDecimal sanctionedAmount;
    @JsonProperty("allocated_amount")
    private BigDecimal allocatedAmount;
    @JsonProperty("available_amount")
    private BigDecimal availableAmount;
    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;
}

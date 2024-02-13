package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.egov.web.models.enums.AllocationType;
import org.egov.web.models.enums.Type;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Allocation extends ExchangeCode {
    @JsonProperty("id")
    private String id;

    @JsonProperty("location_code")
    @NotNull
    @Size(min = 2, max = 64)
    private String locationCode;

    @JsonProperty("program_code")
    @NotNull
    @Size(min = 2, max = 64)
    private String programCode;

    @JsonProperty("sanction_id")
    @NotNull
    @Size(min = 2, max = 64)
    private String sanctionId;

    @JsonProperty("amount")
    @NotNull
    private BigDecimal amount;

    @JsonProperty("type")
    @NotNull
    private AllocationType type;

    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
}

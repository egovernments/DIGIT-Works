package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.web.models.enums.AllocationType;
import org.egov.web.models.enums.Type;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
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
    private String sanctionId;

    @JsonProperty("net_amount")
    @NotNull
    private BigDecimal netAmount;

    @JsonProperty("gross_amount")
    @NotNull
    private BigDecimal grossAmount;

    @JsonProperty("allocation_type")
    private AllocationType allocationType;

    @JsonProperty("children")
    private List<Allocation> children;

    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
}

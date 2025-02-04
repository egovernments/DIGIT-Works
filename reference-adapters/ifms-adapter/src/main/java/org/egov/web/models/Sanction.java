package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sanction extends ExchangeCode {
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
    @JsonProperty("net_amount")
    private BigDecimal netAmount;
    @JsonProperty("gross_amount")
    private BigDecimal grossAmount;
    @JsonProperty("available_amount")
    private BigDecimal availableAmount;
    @JsonProperty("children")
    private List<Sanction> children;
    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;
}

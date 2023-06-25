package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Sanction {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("hoaCode")
    private String hoaCode = null;

    @JsonProperty("ddoCode")
    private String ddoCode = null;

    @JsonProperty("masterAllotmentId")
    private String masterAllotmentId = null;

    @JsonProperty("sanctionedAmount")
    private BigDecimal sanctionedAmount = null;

    @JsonProperty("financialYear")
    private String financialYear = null;


    @JsonProperty("additionalDetails")
    private Object additionalDetails;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails;


}

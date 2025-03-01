package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SanctionDetail {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("hoaCode")
    private String hoaCode;

    @JsonProperty("ddoCode")
    private String ddoCode;

    @JsonProperty("programCode")
    private String programCode;

    @JsonProperty("masterAllotmentId")
    private String masterAllotmentId;

    @JsonProperty("sanctionedAmount")
    private BigDecimal sanctionedAmount;

    @JsonProperty("financialYear")
    private String financialYear;

    @JsonProperty("additionalDetails")
    private Object additionalDetails;

    @JsonProperty("fundsSummary")
    private FundsSummary fundsSummary;

    @JsonProperty("allotmentDetails")
    private List<Allotment> allotmentDetails;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;


}

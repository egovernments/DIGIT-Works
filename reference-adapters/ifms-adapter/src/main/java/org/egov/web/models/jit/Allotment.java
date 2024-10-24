package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;

import javax.validation.Valid;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Allotment {

    @JsonProperty("id")

    private String id = null;

    @JsonProperty("tenantId")

    private String tenantId = null;

    @JsonProperty("programCode")

    private String programCode = null;

    @JsonProperty("sanctionId")

    private String sanctionId = null;

    @JsonProperty("additionalDetails")

    private Object additionalDetails;

    @JsonProperty("allotmentSerialNo")

    private int allotmentSerialNo;

    @JsonProperty("decimalAllottedAmount")

    private BigDecimal decimalAllottedAmount = null;

    @JsonProperty("decimalSanctionBalance")

    private BigDecimal decimalSanctionBalance = null;

    @JsonProperty("allotmentDateTimeStamp")

    private Long allotmentDateTimeStamp = null;

    @JsonProperty("auditDetails")

    private AuditDetails auditDetails;

    // JIT-VA response fields
    @JsonProperty("ddoCode")

    private String hoaCode = null;

    @JsonProperty("schemeCode")

    private String schemeCode;

    @JsonProperty("schemeName")

    private String schemeName;

    @JsonProperty("granteeAgCode")

    private String granteeAgCode;

    @JsonProperty("granteeName")

    private String granteeName;

    @JsonProperty("ssuId")

    private String ssuId;

    @JsonProperty("ssuOffice")

    private String ssuOffice;

    @JsonProperty("allotmentDate")

    private String allotmentDate;

    @JsonProperty("allotmentTxnSlNo")

    private String allotmentTxnSlNo;

    @JsonProperty("allotmentAmount")

    private String allotmentAmount;

    @JsonProperty("availableBalance")

    private String availableBalance;

    @JsonProperty("allotmentTxnType")

    private String allotmentTxnType;

    @JsonProperty("mstAllotmentDistId")

    private String mstAllotmentDistId;

    @JsonProperty("ssuAllotmentId")

    private String ssuAllotmentId;

}

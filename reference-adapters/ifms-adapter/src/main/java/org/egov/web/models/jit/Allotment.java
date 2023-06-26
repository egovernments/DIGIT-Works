package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.data.query.annotations.Exclude;

import javax.validation.Valid;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Allotment {

    @JsonProperty("id")
    @Exclude
    private String id = null;

    @JsonProperty("tenantId")
    @Exclude
    private String tenantId = null;

    @JsonProperty("sanctionId")
    @Exclude
    private String sanctionId = null;

    @JsonProperty("additionalDetails")
    @Exclude
    private Object additionalDetails;

    @JsonProperty("allotmentSerialNo")
    @Exclude
    private int allotmentSerialNo;

    @JsonProperty("decimalAllottedAmount")
    @Exclude
    private BigDecimal decimalAllottedAmount = null;

    @JsonProperty("decimalSanctionBalance")
    @Exclude
    private BigDecimal decimalSanctionBalance = null;

    @JsonProperty("allotmentDateTimeStamp")
    @Exclude
    private Long allotmentDateTimeStamp = null;

    @JsonProperty("auditDetails")
    @Exclude
    private AuditDetails auditDetails;

    // JIT-VA response fields
    @JsonProperty("ddoCode")
    @Exclude
    private String hoaCode = null;

    @JsonProperty("schemeCode")
    @Exclude
    private String schemeCode;

    @JsonProperty("schemeName")
    @Exclude
    private String schemeName;

    @JsonProperty("granteeAgCode")
    @Exclude
    private String granteeAgCode;

    @JsonProperty("granteeName")
    @Exclude
    private String granteeName;

    @JsonProperty("ssuId")
    @Exclude
    private String ssuId;

    @JsonProperty("ssuOffice")
    @Exclude
    private String ssuOffice;

    @JsonProperty("allotmentDate")
    @Exclude
    private String allotmentDate;

    @JsonProperty("allotmentTxnSlNo")
    @Exclude
    private String allotmentTxnSlNo;

    @JsonProperty("allotmentAmount")
    @Exclude
    private String allotmentAmount;

    @JsonProperty("availableBalance")
    @Exclude
    private String availableBalance;

    @JsonProperty("allotmentTxnType")
    @Exclude
    private String allotmentTxnType;

    @JsonProperty("mstAllotmentDistId")
    @Exclude
    private String mstAllotmentDistId;

    @JsonProperty("ssuAllotmentId")
    @Exclude
    private String ssuAllotmentId;

}

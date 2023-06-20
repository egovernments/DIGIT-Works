package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PIRequest {
    @JsonProperty("jitBillNo")
    private String jitBillNo;
    @JsonProperty("jitBillDate")
    private String jitBillDate;
    @JsonProperty("jitBillDdoCode")
    private String jitBillDdoCode;
    @JsonProperty("granteeAgCode")
    private String granteeAgCode;
    @JsonProperty("schemeCode")
    private String schemeCode;
    @JsonProperty("hoa")
    private String hoa;
    @JsonProperty("ssuIaId")
    private String ssuIaId;
    @JsonProperty("mstAllotmentDistId")
    private String mstAllotmentDistId;
    @JsonProperty("ssuAllotmentId")
    private String ssuAllotmentId;
    @JsonProperty("allotmentTxnSlNo")
    private String allotmentTxnSlNo;
    @JsonProperty("billGrossAmount")
    private String billGrossAmount;
    @JsonProperty("billNetAmount")
    private String billNetAmount;
    @JsonProperty("billNumberOfBenf")
    private String billNumberOfBenf;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("beneficiaryDetails")
    private List<PiBeneficiary> beneficiaryDetails;

}

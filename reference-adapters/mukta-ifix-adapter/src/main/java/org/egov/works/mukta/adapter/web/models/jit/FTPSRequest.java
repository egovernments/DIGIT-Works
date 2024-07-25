package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FTPSRequest {

    @JsonProperty("jitCorBillNo")
    private String jitCorBillNo;

    @JsonProperty("jitCorBillDate")
    private String jitCorBillDate;

    @JsonProperty("jitCorBillDeptCode")
    private String jitCorBillDeptCode;

    @JsonProperty("jitOrgBillRefNo")
    private String jitOrgBillRefNo;

    @JsonProperty("jitOrgBillNo")
    private String jitOrgBillNo;

    @JsonProperty("jitOrgBillDate")
    private String jitOrgBillDate;

}

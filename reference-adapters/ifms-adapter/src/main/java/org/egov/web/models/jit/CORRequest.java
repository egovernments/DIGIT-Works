package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.data.query.annotations.Exclude;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CORRequest {

    @JsonProperty("jitCorBillNo")
    @Exclude
    private String jitCorBillNo;

    @JsonProperty("jitCorBillDate")
    @Exclude
    private String jitCorBillDate;

    @JsonProperty("jitCorBillDeptCode")
    @Exclude
    private String jitCorBillDeptCode;

    @JsonProperty("jitOrgBillRefNo")
    @Exclude
    private String jitOrgBillRefNo;

    @JsonProperty("jitOrgBillNo")
    @Exclude
    private String jitOrgBillNo;

    @JsonProperty("jitOrgBillDate")
    @Exclude
    private String jitOrgBillDate;

    @JsonProperty("beneficiaryDtls")
    @Exclude
    private List<CORBeneficiaryDetails> beneficiaryDtls;

}

package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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

    @JsonProperty("beneficiaryDtls")
    
    private List<CORBeneficiaryDetails> beneficiaryDtls;

}

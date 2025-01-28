package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CORBeneficiaryDetails {

    @JsonProperty("benefId")
    
    private String benefId;

    @JsonProperty("jitCurBillRefNo")
    
    private String jitCurBillRefNo;

    @JsonProperty("orgAccountNo")
    
    private String orgAccountNo;

    @JsonProperty("orgIfsc")
    
    private String orgIfsc;

    @JsonProperty("correctedAccountNo")
    
    private String correctedAccountNo;

    @JsonProperty("correctedIfsc")
    
    private String correctedIfsc;

    @JsonProperty("curAccountNo")
    
    private String curAccountNo;

    @JsonProperty("curIfsc")
    
    private String curIfsc;

}

package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.data.query.annotations.Exclude;

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
    @Exclude
    private String benefId;

    @JsonProperty("jitCurBillRefNo")
    @Exclude
    private String jitCurBillRefNo;

    @JsonProperty("orgAccountNo")
    @Exclude
    private String orgAccountNo;

    @JsonProperty("orgIfsc")
    @Exclude
    private String orgIfsc;

    @JsonProperty("correctedAccountNo")
    @Exclude
    private String correctedAccountNo;

    @JsonProperty("correctedIfsc")
    @Exclude
    private String correctedIfsc;

    @JsonProperty("curAccountNo")
    @Exclude
    private String curAccountNo;

    @JsonProperty("curIfsc")
    @Exclude
    private String curIfsc;

}

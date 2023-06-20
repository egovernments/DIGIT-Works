package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PiBeneficiary {
    @JsonProperty("benefId")
    private String benefId;
    @JsonProperty("benefName")
    private String benefName;
    @JsonProperty("benfAcctNo")
    private String benfAcctNo;
    @JsonProperty("benfBankIfscCode")
    private String benfBankIfscCode;
    @JsonProperty("benfMobileNo")
    private String benfMobileNo;
    @JsonProperty("benfEmailId")
    private String benfEmailId = "";
    @JsonProperty("benfAddress")
    private String benfAddress;
    @JsonProperty("benfAccountType")
    private String benfAccountType;
    @JsonProperty("benfAmount")
    private String benfAmount;
    @JsonProperty("panNo")
    private String panNo = "";
    @JsonProperty("adhaarNumber")
    private String adhaarNumber = "";
    @JsonProperty("purpose")
    private String purpose;

}

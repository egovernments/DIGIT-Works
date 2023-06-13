package org.egov.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Beneficiary {

    private String id;
    private String name;
    private String accountNumber;
    private String ifscCode;
    private String mobileNumber;
    private String address;
    private String accountType;
    private Long amount;
    private String purpose;

}

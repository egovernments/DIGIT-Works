package org.egov.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BeneficiaryTransferStatus {

    private String accountNumber;
    private String ifscCode;
    private String rbiSequenceNumber;
    private Long sequenceDate;
    private String endToEndId;
    private String status;

}

package org.egov.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentStatus {

    private String billNumber;
    private Long billDate;
    private String voucherNumber;
    private Long voucherDate;
    private List<BeneficiaryTransferStatus> beneficiaryTransferStatuses;

}

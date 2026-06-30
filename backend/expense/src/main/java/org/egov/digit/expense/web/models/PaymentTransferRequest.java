package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransferRequest {

    private String amount;
    private String currency;
    private String externalId;
    private Payee payee;
    private String payerMessage;
    private String payeeNote;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payee {
        private String partyIdType; // Should be "MSISDN"
        private String partyId;
    }
}
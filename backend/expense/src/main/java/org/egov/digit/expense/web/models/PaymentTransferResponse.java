package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.digit.expense.web.models.enums.ResponseStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransferResponse {

    private String amount;
    private String currency;
    private String externalId;
    private Payee payee;
    private String payerMessage;
    private String payeeNote;
    private String status;
    private String reason;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payee {
        private String partyIdType; // Typically "MSISDN"
        private String partyId;
    }
}
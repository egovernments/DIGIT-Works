package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Disbursement status reason codes
 */
public enum DisburseStatusReasonCode {
    REFERENCE_ID_INVALID("rjct.reference_id.invalid"), REFERENCE_ID_DUPLICATE("rjct.reference_id.duplicate"), TIMESTAMP_INVALID("rjct.timestamp.invalid"), PAYER_FA_INVALID("rjct.payer_fa.invalid"), PAYEE_FA_INVALID("rjct.payee_fa.invalid"), AMOUNT_INVALID("rjct.amount.invalid"), SCHEDULE_TS_INVALID("rjct.schedule_ts.invalid"), CURRENCY_CODE_INVALID("rjct.currency_code.invalid");

    private final String value;

    DisburseStatusReasonCode(String value) {
        this.value = value;
    }

    @JsonCreator
    public static DisburseStatusReasonCode fromValue(String text) {
        for (DisburseStatusReasonCode b : DisburseStatusReasonCode.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}

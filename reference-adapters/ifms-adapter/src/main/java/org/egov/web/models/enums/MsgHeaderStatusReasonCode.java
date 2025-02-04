package org.egov.web.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MsgHeaderStatusReasonCode {
    RJCT_VERSION_INVALID("rjct.version.invalid"),
    RJCT_MESSAGE_ID_DUPLICATE("rjct.message_id.duplicate"),
    RJCT_MESSAGE_TS_INVALID("rjct.message_ts.invalid"),
    RJCT_ACTION_INVALID("rjct.action.invalid"),
    RJCT_ACTION_NOT_SUPPORTED("rjct.action.not_supported"),
    RJCT_TOTAL_COUNT_INVALID("rjct.total_count.invalid"),
    RJCT_TOTAL_COUNT_LIMIT_EXCEEDED("rjct.total_count.limit_exceeded"),
    RJCT_ERRORS_TOO_MANY("rjct.errors.too_many");

    private final String value;

    MsgHeaderStatusReasonCode(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}

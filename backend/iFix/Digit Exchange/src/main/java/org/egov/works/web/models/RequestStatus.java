package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Request (e.g disburse, link, unlink, resolve, issue, search, verify, etc.,) status: <br> 1. rcvd: Received; Request received <br> 2. pdng: Pending; Request initiated <br> 3. succ: Success; Request successful <br> 4. rjct: Rejected; Request rejected
 */
public enum RequestStatus {
    RCVD("rcvd"), PDNG("pdng"), SUCC("succ"), RJCT("rjct");

    private final String value;

    RequestStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static RequestStatus fromValue(String text) {
        for (RequestStatus b : RequestStatus.values()) {
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

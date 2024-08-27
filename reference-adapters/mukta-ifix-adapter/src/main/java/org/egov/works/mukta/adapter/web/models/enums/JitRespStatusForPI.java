package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;

/**
 * enum value for the api response results for payment instruction from jit
 */
public enum JitRespStatusForPI {
    STATUS_LOG_PI_NO_FUNDS("STATUS_LOG_PI_NO_FUNDS"),
    STATUS_LOG_PI_NO_RESPONSE("STATUS_LOG_PI_NO_RESPONSE"),
    STATUS_LOG_PI_ERROR("STATUS_LOG_PI_ERROR"),
    STATUS_LOG_PI_SUCCESS("STATUS_LOG_PI_SUCCESS"),
    STATUS_LOG_PIS_NO_RESPONSE("STATUS_LOG_PIS_NO_RESPONSE"),
    STATUS_LOG_PIS_ERROR("STATUS_LOG_PIS_ERROR"),
    STATUS_LOG_PIS_SUCCESS("STATUS_LOG_PIS_SUCCESS"),
    STATUS_LOG_PIS_REJECTED("STATUS_LOG_PIS_REJECTED"),
    STATUS_LOG_PAG_NO_RESPONSE("STATUS_LOG_PAG_NO_RESPONSE"),
    STATUS_LOG_PAG_ERROR("STATUS_LOG_PAG_ERROR"),
    STATUS_LOG_PAG_SUCCESS("STATUS_LOG_PAG_SUCCESS"),
    STATUS_LOG_PD_NO_RESPONSE("STATUS_LOG_PD_NO_RESPONSE"),
    STATUS_LOG_PD_ERROR("STATUS_LOG_PD_ERROR"),
    STATUS_LOG_PD_SUCCESS("STATUS_LOG_PD_SUCCESS"),
    STATUS_LOG_FD_NO_RESPONSE("STATUS_LOG_FD_NO_RESPONSE"),
    STATUS_LOG_FD_ERROR("STATUS_LOG_FD_ERROR"),
    STATUS_LOG_FD_SUCCESS("STATUS_LOG_FD_SUCCESS"),
    STATUS_LOG_COR_NO_RESPONSE("STATUS_LOG_COR_NO_RESPONSE"),
    STATUS_LOG_COR_ERROR("STATUS_LOG_COR_ERROR"),
    STATUS_LOG_COR_SUCCESS("STATUS_LOG_COR_SUCCESS"),
    STATUS_LOG_FTPS_NO_RESPONSE("STATUS_LOG_FTPS_NO_RESPONSE"),
    STATUS_LOG_FTPS_ERROR("STATUS_LOG_FTPS_ERROR"),
    STATUS_LOG_FTPS_SUCCESS("STATUS_LOG_FTPS_SUCCESS"),
    STATUS_LOG_FTFPS_NO_RESPONSE("STATUS_LOG_FTFPS_NO_RESPONSE"),
    STATUS_LOG_FTFPS_ERROR("STATUS_LOG_FTFPS_ERROR"),
    STATUS_LOG_FTFPS_SUCCESS("STATUS_LOG_FTFPS_SUCCESS");


    private String value;

    JitRespStatusForPI(String value) {
        this.value = value;
    }

    @JsonCreator
    @NotNull
    public static JitRespStatusForPI fromValue(String text) {
        for (JitRespStatusForPI b : JitRespStatusForPI.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
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

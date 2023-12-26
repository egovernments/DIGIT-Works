package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

/**
 * Commumication layer Asyn errors that are returned as part of message acknowledgement. 1. Messages that are not parsable or message integrity check fails. 2. This object may be used across all transport layer protocols (https, sftp, messaging, etc,) to ack the receipt of a message. 3. Business context and related validation is NOT in scope of this error object.
 */
@Schema(description = "Commumication layer Asyn errors that are returned as part of message acknowledgement. 1. Messages that are not parsable or message integrity check fails. 2. This object may be used across all transport layer protocols (https, sftp, messaging, etc,) to ack the receipt of a message. 3. Business context and related validation is NOT in scope of this error object. ")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-12-26T11:42:32.468+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
    @JsonProperty("code")

    private CodeEnum code = null;
    @JsonProperty("message")

    @Size(max = 999)
    private String message = null;

    /**
     * Standard error code
     */
    public enum CodeEnum {
        REQUEST_BAD("err.request.bad"),

        REQUEST_UNAUTHORIZED("err.request.unauthorized"),

        REQUEST_FORBIDDEN("err.request.forbidden"),

        REQUEST_NOT_FOUND("err.request.not_found"),

        REQUEST_TIMEOUT("err.request.timeout"),

        VERSION_NOT_SUPPORTED("err.version.not_supported"),

        REQUEST_TOO_MANY_REQUESTS("err.request.too_many_requests"),

        SENDER_ID_INVALID("err.sender_id.invalid"),

        SENDER_URI_INVALID("err.sender_uri.invalid"),

        RECEIVER_ID_INVALID("err.receiver_id.invalid"),

        SIGNATURE_MISSING("err.signature.missing"),

        SIGNATURE_INVALID("err.signature.invalid"),

        ENCRYPTION_INVALID("err.encryption.invalid"),

        SERVICE_UNAVAILABLE("err.service.unavailable");

        private String value;

        CodeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static CodeEnum fromValue(String text) {
            for (CodeEnum b : CodeEnum.values()) {
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


}

package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * ResponseHeader should be used to carry metadata information about the response from the server. apiId, ver and msgId in ResponseHeader should always correspond to the same values in respective request&#x27;s RequestHeader.
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseHeader {
    @JsonProperty("ts")

    private Long ts = null;

    @JsonProperty("resMsgId")
    @NotNull

    @Size(max = 256)
    private String resMsgId = null;

    @JsonProperty("msgId")
    @NotNull

    @Size(max = 256)
    private String msgId = null;
    @JsonProperty("status")
    @NotNull

    private StatusEnum status = null;
    @JsonProperty("signature")

    private String signature = null;
    @JsonProperty("error")

    @Valid
    private Error error = null;
    @JsonProperty("information")

    private Object information = null;
    @JsonProperty("debug")

    private Object debug = null;
    @JsonProperty("additionalInfo")

    private Object additionalInfo = null;

    /**
     * status of request processing
     */
    public enum StatusEnum {
        COMPLETED("COMPLETED"),

        ACCEPTED("ACCEPTED"),

        FAILED("FAILED");

        private final String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
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

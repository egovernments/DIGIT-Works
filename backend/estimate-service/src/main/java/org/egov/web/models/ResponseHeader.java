package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * ResponseHeader should be used to carry metadata information about the response from the server. apiId, ver and msgId in ResponseHeader should always correspond to the same values in respective request&#39;s RequestHeader.
 */
@ApiModel(description = "ResponseHeader should be used to carry metadata information about the response from the server. apiId, ver and msgId in ResponseHeader should always correspond to the same values in respective request's RequestHeader.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-07-27T14:01:35.043+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseHeader {

    @JsonProperty("ts")
    private Long ts = null;

    @JsonProperty("resMsgId")
    private String resMsgId = null;

    @JsonProperty("msgId")
    private String msgId = null;

    /**
     * status of request processing
     */
    public enum StatusEnum {
        COMPLETED("COMPLETED"),

        ACCEPTED("ACCEPTED"),

        FAILED("FAILED");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
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
    }

    @JsonProperty("status")
    private StatusEnum status = null;

    @JsonProperty("signature")
    private String signature = null;

    @JsonProperty("error")
    private Error error = null;

    @JsonProperty("information")
    private Object information = null;

    @JsonProperty("debug")
    private Object debug = null;

    @JsonProperty("additionalInfo")
    private Object additionalInfo = null;
}


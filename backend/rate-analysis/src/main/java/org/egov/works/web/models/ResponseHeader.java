package org.egov.works.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.egov.works.web.models.Error;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * ResponseHeader should be used to carry metadata information about the response from the server. apiId, ver and msgId in ResponseHeader should always correspond to the same values in respective request&#x27;s RequestHeader.
 */
@Schema(description = "ResponseHeader should be used to carry metadata information about the response from the server. apiId, ver and msgId in ResponseHeader should always correspond to the same values in respective request's RequestHeader.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseHeader   {
        @JsonProperty("ts")

                private Long ts = null;

        @JsonProperty("resMsgId")
          @NotNull

        @Size(max=256)         private String resMsgId = null;

        @JsonProperty("msgId")
          @NotNull

        @Size(max=256)         private String msgId = null;

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
            }        @JsonProperty("status")
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


}

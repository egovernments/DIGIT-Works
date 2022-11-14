package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * ResponseHeader should be used to carry metadata information about the response from the server. apiId, ver and msgId in ResponseHeader should always correspond to the same values in respective request&#39;s RequestHeader.
 */
@ApiModel(description = "ResponseHeader should be used to carry metadata information about the response from the server. apiId, ver and msgId in ResponseHeader should always correspond to the same values in respective request's RequestHeader.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T14:44:21.051+05:30")

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

    @JsonProperty("status")
    private Object status = null;

    @JsonProperty("signature")
    private String signature = null;

    @JsonProperty("error")
    private Object error = null;

    @JsonProperty("information")
    private Object information = null;

    @JsonProperty("debug")
    private Object debug = null;

    @JsonProperty("additionalInfo")
    private Object additionalInfo = null;


}


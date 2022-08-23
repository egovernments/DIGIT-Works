package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * RequestHeader should be used to carry meta information about the requests to the server as described in the fields below. All eGov APIs will use requestHeader as a part of the request body to carry this meta information. Some of this information will be returned back from the server as part of the ResponseHeader in the response body to ensure correlation.
 */
@ApiModel(description = "RequestHeader should be used to carry meta information about the requests to the server as described in the fields below. All eGov APIs will use requestHeader as a part of the request body to carry this meta information. Some of this information will be returned back from the server as part of the ResponseHeader in the response body to ensure correlation.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-07-27T14:01:35.043+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestHeader {

    @JsonProperty("apiInfo")
    private APIInfo apiInfo = null;

    @JsonProperty("deviceDetail")
    private DeviceDetail deviceDetail = null;

    @JsonProperty("ts")
    private Long ts = null;

    @JsonProperty("action")
    private String action = null;

    @JsonProperty("key")
    private String key = null;

    @JsonProperty("msgId")
    private String msgId = null;

    @JsonProperty("requesterId")
    private String requesterId = null;

    @JsonProperty("authToken")
    private String authToken = null;

    @JsonProperty("userInfo")
    private UserInfo userInfo = null;

    @JsonProperty("correlationId")
    private String correlationId = null;

    @JsonProperty("signature")
    private String signature = null;

}


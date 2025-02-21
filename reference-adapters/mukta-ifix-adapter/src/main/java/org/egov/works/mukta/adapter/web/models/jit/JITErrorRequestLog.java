package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.works.mukta.adapter.web.models.enums.JITServiceId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JITErrorRequestLog {

    @JsonProperty("id")
    private String id;

    @JsonProperty("serviceId")
    private JITServiceId serviceId;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("jitEncRequest")
    private String jitEncRequest;

    @JsonProperty("errorMsg")
    private String errorMsg;

    @JsonProperty("decryptionRek")
    private String decryptionRek;

    @JsonProperty("encryptionRek")
    private String encryptionRek;

    @JsonProperty("authToken")
    private String authToken;

    @JsonProperty("sekString")
    private String sekString;

    @JsonProperty("createdtime")
    private Long createdtime;


}

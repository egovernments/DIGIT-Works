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
public class JITRequestLog {

    @JsonProperty("id")
    private String id;

    @JsonProperty("serviceId")
    private JITServiceId serviceId;

    @JsonProperty("jitBillNo")
    private String jitBillNo;

    @JsonProperty("encRequest")
    private String encRequest;

    @JsonProperty("decryptionRek")
    private String decryptionRek;

    @JsonProperty("encResponse")
    private String encResponse;

    @JsonProperty("createdtime")
    private Long createdtime;


}

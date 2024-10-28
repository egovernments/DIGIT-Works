package org.egov.works.services.common.models.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestInfoWrapper {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;


}


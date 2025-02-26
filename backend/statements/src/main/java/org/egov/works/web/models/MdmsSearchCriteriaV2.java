package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MdmsSearchCriteriaV2 {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("MdmsCriteria")
    private MdmsCriteriaV2 mdmsCriteria;
}

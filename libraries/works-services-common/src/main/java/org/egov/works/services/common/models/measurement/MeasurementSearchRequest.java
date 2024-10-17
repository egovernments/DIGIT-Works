package org.egov.works.services.common.models.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

import jakarta.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementSearchRequest {
    @JsonProperty("RequestInfo")

    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("criteria")

    @Valid
    private MeasurementCriteria criteria = null;

    @JsonProperty("pagination")

    @Valid
    private Pagination pagination = null;


}

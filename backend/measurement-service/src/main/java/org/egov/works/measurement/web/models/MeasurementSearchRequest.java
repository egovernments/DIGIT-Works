package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

/**
 * MeasurementSearchRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MeasurementSearchRequest {

    @JsonProperty("RequestInfo")
    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("criteria")
    @Valid
    @NotNull
    private MeasurementCriteria criteria = null;

    @JsonProperty("pagination")
    @Valid
    private Pagination pagination = null;

}

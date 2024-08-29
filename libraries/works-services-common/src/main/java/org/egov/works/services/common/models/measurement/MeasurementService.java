package org.egov.works.services.common.models.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.Workflow;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeasurementService extends Measurement {

    @JsonProperty("wfStatus")
    private String wfStatus = null;

    @JsonProperty("workflow")
    private Workflow workflow = null;


}
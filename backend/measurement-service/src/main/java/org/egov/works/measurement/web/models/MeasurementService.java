package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.Workflow;
import org.springframework.validation.annotation.Validated;

/**
 * MeasurementRegistry
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-15T11:39:57.604+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeasurementService extends Measurement  {

    @JsonProperty("wfStatus")
    private String wfStatus = null;

    @JsonProperty("workflow")
//    @Valid
    private Workflow workflow = null;


}
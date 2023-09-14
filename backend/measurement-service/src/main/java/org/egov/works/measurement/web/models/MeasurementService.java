package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.Workflow;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * MeasurementService
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementService   {
        @JsonProperty("allOf")

          @Valid
                private Measurement allOf = null;

        @JsonProperty("workflow")

          @Valid
                private Workflow workflow = null;


}

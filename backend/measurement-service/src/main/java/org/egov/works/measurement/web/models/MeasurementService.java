package org.egov.works.measurement.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.*;
import org.egov.works.measurement.web.models.AuditDetails;
import org.egov.works.measurement.web.models.Measure;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.Workflow;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MeasurementService
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
    @Valid
    private Workflow workflow = null;


}
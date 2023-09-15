package org.egov.works.measurement.web.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MeasurementCriteria
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MeasurementCriteria {

    @JsonProperty("referenceId")
    private List<String> referenceId = null;

    @JsonProperty("measurementNumber")
    private String measurementNumber = null;

    @JsonProperty("ids")
    private List<String> ids = null;


    public MeasurementCriteria addReferenceIdItem(String referenceIdItem) {
        if (this.referenceId == null) {
            this.referenceId = new ArrayList<>();
        }
        this.referenceId.add(referenceIdItem);
        return this;
    }

    public MeasurementCriteria addIdsItem(String idsItem) {
        if (this.ids == null) {
            this.ids = new ArrayList<>();
        }
        this.ids.add(idsItem);
        return this;
    }

}

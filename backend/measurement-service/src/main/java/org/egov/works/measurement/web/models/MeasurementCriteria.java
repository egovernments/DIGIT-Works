package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * MeasurementCriteria
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
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

    @JsonProperty("tenantId")
    @Valid
    @NotNull
    private String tenantId = null;

    @JsonProperty("fromDate")
    private Long fromDate = null;

    @JsonProperty("toDate")
    private Long toDate = null;

    @JsonProperty("isActive")
    private Boolean isActive = null;

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

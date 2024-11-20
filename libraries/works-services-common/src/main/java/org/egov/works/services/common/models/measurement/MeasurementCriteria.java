package org.egov.works.services.common.models.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

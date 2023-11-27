package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This defines a measurement with or without detailed measures.
 */
@Schema(description = "This defines a measurement with or without detailed measures.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Measurement {

    @JsonProperty("id")
    @Valid
    @Size(max = 64)
    private String id = null;

    @JsonProperty("tenantId")
    @Size(min = 2, max = 64)
    @NotNull
    private String tenantId = null;

    @JsonProperty("measurementNumber")
    @Size(min = 2, max = 64)
    private String measurementNumber = null;

    @JsonProperty("physicalRefNumber")
    @Size(min = 2, max = 100)
    private String physicalRefNumber = null;

    @JsonProperty("referenceId")
    @NotNull
    @Size(min = 2, max = 64)
    private String referenceId = null;

    @JsonProperty("entryDate")
    @NotNull
    @Valid
    private BigDecimal entryDate = null;

    @JsonProperty("measures")
    @Valid
    private List<Measure> measures = null;

    @JsonProperty("isActive")
    private Boolean isActive = true;

    @JsonProperty("documents")
    @Valid
    private List<Document> documents = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    public Measurement addMeasuresItem(Measure measuresItem) {
        if (this.measures == null) {
            this.measures = new ArrayList<>();
        }
        this.measures.add(measuresItem);
        return this;
    }

}

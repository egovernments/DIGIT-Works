package org.egov.works.services.common.models.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Measure {
    @JsonProperty("id")
    @Size(max = 64)
    @Valid
    private String id = null;

    @JsonProperty("referenceId")

    @Size(min = 2, max = 64)
    private String referenceId = null;

    @JsonProperty("targetId")
    @NotNull

    @Size(min = 2, max = 64)
    private String targetId = null;

    @JsonProperty("length")

    @Valid
    private BigDecimal length = new BigDecimal(1);

    @JsonProperty("breadth")

    @Valid
    private BigDecimal breadth = new BigDecimal(1);

    @JsonProperty("height")

    @Valid
    private BigDecimal height = new BigDecimal(1);

    @JsonProperty("numItems")

    @Valid
    private BigDecimal numItems = new BigDecimal(1);

    @JsonProperty("currentValue")
    @NotNull

    @Valid
    private BigDecimal currentValue = null;

    @JsonProperty("cumulativeValue")

    @Valid
    private BigDecimal cumulativeValue = null;

    @JsonProperty("isActive")

    private Boolean isActive = null;

    @JsonProperty("comments")

    @Size(min = 2, max = 256)
    private String comments = null;

    @JsonProperty("documents")
    @Valid
    @Size(min = 1)
    private List<Document> documents = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")

    private Object additionalDetails = null;


    public Measure addDocumentsItem(Document documentsItem) {
        if (this.documents == null) {
            this.documents = new ArrayList<>();
        }
        this.documents.add(documentsItem);
        return this;
    }

}

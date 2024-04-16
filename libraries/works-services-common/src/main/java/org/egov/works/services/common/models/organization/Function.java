package org.egov.works.services.common.models.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.Document;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Function {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("orgId")
    private String orgId = null;

    @JsonProperty("applicationNumber")
    private String applicationNumber = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("category")
    @Size(min = 2, max = 64)
    private String category = null;

    @JsonProperty("class")
    @Size(min = 2, max = 64)
    private String propertyClass = null;

    @JsonProperty("validFrom")
    private BigDecimal validFrom = null;

    @JsonProperty("validTo")
    private BigDecimal validTo = null;

    @JsonProperty("applicationStatus")
    private ApplicationStatus applicationStatus = null;

    @JsonProperty("wfStatus")
    private String wfStatus = null;

    @JsonProperty("isActive")
    private Boolean isActive = null;

    @JsonProperty("documents")
    @Valid
    private List<Document> documents = null;//upsert

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    public Function addDocumentsItem(Document documentsItem) {
        if (this.documents == null) {
            this.documents = new ArrayList<>();
        }
        this.documents.add(documentsItem);
        return this;
    }

}

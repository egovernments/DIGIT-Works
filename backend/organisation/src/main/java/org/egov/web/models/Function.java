package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the functions of an organisation
 */
@ApiModel(description = "Represents the functions of an organisation")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

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

package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "A Object which holds the meta about the bill")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillMeta {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("serviceCode")
    private String serviceCode = null;

    @JsonProperty("contractId")
    private String contractId = null;

    @JsonProperty("musterrollId")
    private String musterrollId = null;

    @JsonProperty("billType")
    private String billType = null;

    @JsonProperty("billId")
    private String billId = null;

    @JsonProperty("isActive")
    private Boolean isActive = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("billAmount")
    @Valid
    private BigDecimal billAmount = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

    @JsonProperty("documents")
    @Valid
    private List<Document> documents = null;

    public BillMeta addDocumentsItem(Document documentsItem) {
        if (this.documents == null) {
            this.documents = new ArrayList<>();
        }
        this.documents.add(documentsItem);
        return this;
    }
}

package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.Document;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Account details
 */
@Schema(description = "Account details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountDetails {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("accountHolderName")
    @Size(min = 2, max = 64)
    private String accountHolderName = null;

    @JsonProperty("accountNumber")
    @NotNull
    @Size(min = 2, max = 64)
    private String accountNumber = null;

    @JsonProperty("accountType")
    @Size(min = 2, max = 64)
    private String accountType = null;

    @JsonProperty("isPrimary")
    private Boolean isPrimary = true;

    @JsonProperty("bankBranchIdentifier")
    @NotNull
    @Valid
    private BankBranchIdentifier bankBranchIdentifier = null;

    @JsonProperty("isActive")
    private Boolean isActive = null;

    @JsonProperty("documents")
    @Valid
    private List<Document> documents = null;

    @JsonProperty("additionalFields")
    private Object additionalFields = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;


    public BankAccountDetails addDocumentsItem(Document documentsItem) {
        if (this.documents == null) {
            this.documents = new ArrayList<>();
        }
        this.documents.add(documentsItem);
        return this;
    }

}

package org.egov.works.services.common.models.bankaccounts;

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
import java.util.ArrayList;
import java.util.List;


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

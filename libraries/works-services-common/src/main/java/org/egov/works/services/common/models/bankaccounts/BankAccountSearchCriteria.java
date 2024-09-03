package org.egov.works.services.common.models.bankaccounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountSearchCriteria {

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("ids")
    private List<String> ids = null;

    @JsonProperty("serviceCode")
    @Size(min = 2, max = 64)
    private String serviceCode = null;

    @JsonProperty("referenceId")
    private List<String> referenceId = null;

    @JsonProperty("accountHolderName")
    @Size(min = 2, max = 64)
    private String accountHolderName = null;

    @JsonProperty("accountNumber")
    private List<String> accountNumber = null;

    @JsonProperty("isActive")
    private Boolean isActive = null;

    @JsonProperty("isPrimary")
    private Boolean isPrimary = null;

    @JsonProperty("bankBranchIdentifierCode")
    @Valid
    private BankBranchIdentifier bankBranchIdentifierCode = null;

    @JsonIgnore
    private Boolean isCountNeeded = false;


    public BankAccountSearchCriteria addIdsItem(String idsItem) {
        if (this.ids == null) {
            this.ids = new ArrayList<>();
        }
        this.ids.add(idsItem);
        return this;
    }

    public BankAccountSearchCriteria addReferenceIdItem(String referenceIdItem) {
        if (this.referenceId == null) {
            this.referenceId = new ArrayList<>();
        }
        this.referenceId.add(referenceIdItem);
        return this;
    }

    public BankAccountSearchCriteria addAccountNumberItem(String accountNumberItem) {
        if (this.accountNumber == null) {
            this.accountNumber = new ArrayList<>();
        }
        this.accountNumber.add(accountNumberItem);
        return this;
    }

}

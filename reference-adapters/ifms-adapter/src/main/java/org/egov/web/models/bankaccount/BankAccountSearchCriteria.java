package org.egov.web.models.bankaccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * BankAccountSearchCriteria
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
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

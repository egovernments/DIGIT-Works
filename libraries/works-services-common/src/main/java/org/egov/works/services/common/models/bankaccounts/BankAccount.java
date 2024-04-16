package org.egov.works.services.common.models.bankaccounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("serviceCode")
    @NotNull
    @Size(min = 2, max = 64)
    private String serviceCode = null;

    @JsonProperty("referenceId")
    @NotNull
    @Size(min = 2, max = 64)
    private String referenceId = null;

    @JsonProperty("bankAccountDetails")
    @Valid
    private List<BankAccountDetails> bankAccountDetails = null;

    @JsonProperty("additionalFields")
    private Object additionalFields = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;


    public BankAccount addBankAccountDetailsItem(BankAccountDetails bankAccountDetailsItem) {
        if (this.bankAccountDetails == null) {
            this.bankAccountDetails = new ArrayList<>();
        }
        this.bankAccountDetails.add(bankAccountDetailsItem);
        return this;
    }

}

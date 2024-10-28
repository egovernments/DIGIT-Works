package org.egov.works.services.common.models.bankaccounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponse {

    @JsonProperty("ResponseInfo")
    @Valid
    private ResponseInfo responseInfo = null;

    @JsonProperty("bankAccounts")
    @Valid
    private List<BankAccount> bankAccounts = null;

    @JsonProperty("pagination")
    @Valid
    private Pagination pagination = null;


    public BankAccountResponse addBankAccountsItem(BankAccount bankAccountsItem) {
        if (this.bankAccounts == null) {
            this.bankAccounts = new ArrayList<>();
        }
        this.bankAccounts.add(bankAccountsItem);
        return this;
    }

}

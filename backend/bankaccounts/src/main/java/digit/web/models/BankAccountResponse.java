package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import digit.web.models.BankAccount;
import digit.web.models.Pagination;
import digit.web.models.ResponseInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * BankAccountResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponse   {
        @JsonProperty("responseInfo")

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

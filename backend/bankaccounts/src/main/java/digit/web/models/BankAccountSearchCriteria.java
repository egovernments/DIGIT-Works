package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import digit.web.models.BankBranchIdentifier;
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
 * BankAccountSearchCriteria
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountSearchCriteria   {
        @JsonProperty("tenantId")
          @NotNull

        @Size(min=2,max=64)         private String tenantId = null;

        @JsonProperty("ids")

                private List<String> ids = null;

        @JsonProperty("serviceCode")

        @Size(min=2,max=64)         private String serviceCode = null;

        @JsonProperty("referenceId")

                private List<String> referenceId = null;

        @JsonProperty("accountHolderName")

        @Size(min=2,max=64)         private String accountHolderName = null;

        @JsonProperty("accountNumber")

                private List<String> accountNumber = null;

        @JsonProperty("isActive")

                private Boolean isActive = null;

        @JsonProperty("isPrimary")

                private Boolean isPrimary = null;

        @JsonProperty("bankBranchIdentifierCode")

          @Valid
                private BankBranchIdentifier bankBranchIdentifierCode = null;


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

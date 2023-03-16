package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * Banking details
 */
@Schema(description = "Banking details")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
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

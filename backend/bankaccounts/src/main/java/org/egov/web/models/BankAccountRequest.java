package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

/**
 * BankAccountRequest
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountRequest {

    @JsonProperty("RequestInfo")
    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("bankAccounts")
    @Valid
    private List<BankAccount> bankAccounts = null;


}

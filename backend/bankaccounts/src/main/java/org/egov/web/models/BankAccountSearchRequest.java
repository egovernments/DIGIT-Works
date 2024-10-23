package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.services.common.models.bankaccounts.Pagination;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * BankAccountSearchRequest
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountSearchRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("bankAccountDetails")
    @Valid
    @NotNull
    private BankAccountSearchCriteria bankAccountDetails = null;

    @JsonProperty("pagination")
    @Valid
    private Pagination pagination = null;


}

package org.egov.web.models.bankaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.egov.web.models.Pagination;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * BankAccountSearchRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountSearchRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("bankAccountDetails")
    @Valid
    private BankAccountSearchCriteria bankAccountDetails = null;

    @JsonProperty("pagination")
    @Valid
    private Pagination pagination = null;


}

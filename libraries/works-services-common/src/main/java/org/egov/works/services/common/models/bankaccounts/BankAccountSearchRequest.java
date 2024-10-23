package org.egov.works.services.common.models.bankaccounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

import jakarta.validation.Valid;


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

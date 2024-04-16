package org.egov.works.services.common.models.bankaccounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankBranchIdentifier {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("type")
    @Size(min = 2, max = 64)
    private String type = null;

    @JsonProperty("code")
    @Size(min = 2, max = 64)
    private String code = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


}

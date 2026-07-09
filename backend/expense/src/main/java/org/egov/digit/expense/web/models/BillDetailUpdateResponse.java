package org.egov.digit.expense.web.models;

import java.util.List;

import jakarta.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response for the bill details partial update API.
 * Contains the successfully merged bill details and any per-detail errors.
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailUpdateResponse {

    @JsonProperty("ResponseInfo")
    @Valid
    private ResponseInfo responseInfo;

    @JsonProperty("billDetails")
    @Valid
    private List<BillDetail> billDetails;

    @JsonProperty("errors")
    private List<BillDetailUpdateError> errors;

    @JsonProperty("warnings")
    private List<BillDetailUpdateError> warnings;
}

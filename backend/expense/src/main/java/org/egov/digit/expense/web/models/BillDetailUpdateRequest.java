package org.egov.digit.expense.web.models;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request for partial update of one or more bill details under a single bill.
 * Only the fields provided in each {@link PartialBillDetail} are updated;
 * null fields are preserved from the database.
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailUpdateRequest {

    @JsonProperty("RequestInfo")
    @Valid
    @NotNull
    private RequestInfo requestInfo;

    @JsonProperty("billId")
    @NotNull
    private String billId;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId;

    @JsonProperty("billDetails")
    @Valid
    @NotNull
    @Size(min = 1)
    private List<PartialBillDetail> billDetails;
}

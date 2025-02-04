package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillStatus {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("referenceId")
    private String referenceId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("error")
    private String error;


}

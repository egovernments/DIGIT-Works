package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailUpdateError {

    @JsonProperty("billDetailId")
    private String billDetailId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;
}

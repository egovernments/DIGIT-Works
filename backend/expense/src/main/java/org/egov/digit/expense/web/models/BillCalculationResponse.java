package org.egov.digit.expense.web.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillCalculationResponse
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2025-07-14T10:15:00.000+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillCalculationResponse {

    @JsonProperty("ResponseInfo")
    @Valid
    private ResponseInfo responseInfo;

    @JsonProperty("bills")
    @Valid
    private List<Bill> bills;

    @JsonProperty("pagination")
    @Valid
    private Pagination pagination;

    @JsonProperty("statusCode")
    private StatusCode statusCode;

    public BillCalculationResponse addBillItem(Bill billItem) {
        if (this.bills == null) {
            this.bills = new ArrayList<>();
        }
        this.bills.add(billItem);
        return this;
    }

    /**
     * Enum for statusCode
     */
    public enum StatusCode {
        INITIATED("INITIATED"),
        INPROGRESS("INPROGRESS"),
        SUCCESSFUL("SUCCESSFUL");

        private final String value;

        StatusCode(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static StatusCode fromValue(String value) {
            for (StatusCode code : StatusCode.values()) {
                if (code.value.equalsIgnoreCase(value)) {
                    return code;
                }
            }
            throw new IllegalArgumentException("Invalid status code: " + value);
        }
    }
}

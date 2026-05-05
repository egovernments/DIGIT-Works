package org.egov.digit.expense.calculator.web.models.report;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Bill details of the individual payee
 */
@Schema(description = "Bill details of the individual payees")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportBillDetail {

    @Schema(description = "slNo")
    private Integer slNo;

    @Schema(description = "individualName")
    private String individualName;

    @Schema(description = "role")
    private String role;

    @Schema(description = "locality")
    private String locality;

    @Schema(description = "idNumber")
    private String idNumber;

    @Schema(description = "mobileNumber")
    private String mobileNumber;

    @JsonIgnore
    @Builder.Default
    private Map<String, BigDecimal> perDayBreakup = new LinkedHashMap<>();

    @JsonAnyGetter
    public Map<String, BigDecimal> getPerDayBreakup() {
        return perDayBreakup;
    }

    @JsonAnySetter
    public void setPerDayEntry(String key, Object value) {
        if (value instanceof Number) {
            perDayBreakup.put(key, new BigDecimal(value.toString()));
        }
    }

    // Stores actual stored bill totals per field; used for PERCENTAGE fields so the
    // total column shows the real payout (not perDayBreakup × days which would be wrong).
    @JsonIgnore
    @Builder.Default
    private Map<String, BigDecimal> totalAmountBreakup = new LinkedHashMap<>();

    @Schema(description = "totalWages")
    private BigDecimal totalWages = BigDecimal.ZERO;

    @Schema(description = "totalNumberOfDays")
    private Float totalNumberOfDays;

    @Schema(description = "totalAmount")
    private BigDecimal totalAmount = BigDecimal.ZERO;
}

package org.egov.digit.expense.calculator.web.models.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

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

    @Schema(description = "wageAmount")
    @Builder.Default
    private BigDecimal wageAmount = BigDecimal.ZERO;

    @Schema(description = "foodAmount")
    private BigDecimal foodAmount = BigDecimal.ZERO;

    @Schema(description = "transportAmount")
    private BigDecimal transportAmount = BigDecimal.ZERO;

    @Schema(description = "totalWages")
    private BigDecimal totalWages = BigDecimal.ZERO;

    @Schema(description = "totalNumberOfDays")
    private Float totalNumberOfDays;

    @Schema(description = "totalAmount")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Schema(description = "bankName")
    private String bankName;

    @Schema(description = "cbnCode")
    private String cbnCode;

    @Schema(description = "bankAccountNumber")
    private String bankAccountNumber;
}

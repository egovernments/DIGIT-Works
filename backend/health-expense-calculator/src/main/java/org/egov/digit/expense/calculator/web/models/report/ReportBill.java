package org.egov.digit.expense.calculator.web.models.report;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Object which holds the info about the expense details
 */
@Schema(description = "A Object which holds the info about the expense details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportBill {

    @JsonProperty("totalAmount")
    @Valid
    @Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @JsonProperty("campaignName")
    private String campaignName;

    @JsonProperty("reportTitle")
    @Valid
    private String reportTitle;

    @JsonProperty("numberOfIndividuals")
    private Integer numberOfIndividuals;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("createdTime")
    private Long createdTime;

    @JsonProperty("billDetails")
    @NotNull
    @Valid
    private List<ReportBillDetail> reportBillDetails;


}
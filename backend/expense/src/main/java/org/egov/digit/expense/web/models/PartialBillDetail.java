package org.egov.digit.expense.web.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.egov.digit.expense.web.models.enums.PaymentStatus;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Partial bill detail DTO for partial update operations.
 * Only {@code id} is required; all other fields are optional.
 * Immutable fields (tenantId, billId, referenceId) are intentionally excluded —
 * they are always preserved from the database regardless of what is sent.
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartialBillDetail {

    @JsonProperty("id")
    @NotNull
    private String id;

    // tenantId, billId, referenceId intentionally excluded — immutable after bill creation

    @JsonProperty("totalAmount")
    @Valid
    private BigDecimal totalAmount;

    @JsonProperty("totalPaidAmount")
    @Valid
    private BigDecimal totalPaidAmount;

    @JsonProperty("paymentStatus")
    private PaymentStatus paymentStatus;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("fromPeriod")
    @Valid
    private Long fromPeriod;

    @JsonProperty("toPeriod")
    @Valid
    private Long toPeriod;

    @JsonProperty("workerId")
    @Size(max = 64)
    private String workerId;

    @JsonProperty("paymentProvider")
    @Size(max = 16)
    private String paymentProvider;

    @JsonProperty("payeeName")
    @Size(max = 256)
    private String payeeName;

    @JsonProperty("payeePhoneNumber")
    @Size(max = 64)
    private String payeePhoneNumber;

    @JsonProperty("bankAccount")
    @Size(max = 128)
    private String bankAccount;

    @JsonProperty("bankCode")
    @Size(max = 64)
    private String bankCode;

    @JsonProperty("beneficiaryCode")
    @Size(max = 128)
    private String beneficiaryCode;

    @JsonProperty("payee")
    @Valid
    private Party payee;

    @JsonProperty("lineItems")
    @Valid
    private List<LineItem> lineItems;

    @JsonProperty("payableLineItems")
    @Valid
    private List<LineItem> payableLineItems;

    @JsonProperty("additionalDetails")
    private Object additionalDetails;
}

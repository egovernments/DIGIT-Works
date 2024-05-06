package org.egov.works.mukta.adapter.web.models.bill;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.works.mukta.adapter.web.models.enums.PaymentStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Line items are the amount breakups for net amounts
 */
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentLineItem {

    @JsonProperty("id")
    private String id;

    @JsonProperty("lineItemId")
    private String lineItemId;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("paidAmount")
    @NotNull
    private BigDecimal paidAmount;

    @JsonProperty("status")
    private PaymentStatus status;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;
}

package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.works.mukta.adapter.web.models.enums.TransactionType;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDetails {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("sanctionId")
    private String sanctionId;

    @JsonProperty("paymentInstId")
    private String paymentInstId;

    @JsonProperty("transactionAmount")
    private BigDecimal transactionAmount;

    @JsonProperty("transactionDate")
    private Long transactionDate;

    @JsonProperty("transactionType")
    private TransactionType transactionType;

    @JsonProperty("additionalDetails")
    private Object additionalDetails;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;

}

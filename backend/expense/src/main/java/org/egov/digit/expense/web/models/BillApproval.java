package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.enums.ApprovalStatus;
import org.egov.digit.expense.web.models.enums.SignatureMethod;
import org.springframework.validation.annotation.Validated;

/**
 * An immutable record of a single bill-approval event, capturing the approver's
 * printed name, signature and role at the time the bill was approved.
 */
@Schema(description = "A record of a single bill approval event, including the approver's printed name and signature")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillApproval {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("billId")
    private String billId;

    @JsonProperty("billNumber")
    private String billNumber;

    @JsonProperty("businessService")
    private String businessService;

    @JsonProperty("approverUuid")
    private String approverUuid;

    @JsonProperty("approverName")
    private String approverName;

    @JsonProperty("role")
    private String role;

    @JsonProperty("signatureMethod")
    private SignatureMethod signatureMethod;

    @JsonProperty("signatureFileStoreId")
    private String signatureFileStoreId;

    @JsonProperty("approvalStatus")
    private ApprovalStatus approvalStatus;

    @JsonProperty("approvedTime")
    private Long approvedTime;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails;
}

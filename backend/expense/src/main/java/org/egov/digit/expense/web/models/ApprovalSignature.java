package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.digit.expense.web.models.enums.SignatureMethod;
import org.springframework.validation.annotation.Validated;

/**
 * Printed name and signature captured from a PAYMENT_APPROVER at the point of bill approval.
 * Submitted alongside the PAYMENT_INITIATION workflow action.
 */
@Schema(description = "Printed name and signature captured from the approver while approving a bill")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApprovalSignature {

    @JsonProperty("printedName")
    @NotBlank
    @Size(min = 1, max = 256)
    private String printedName;

    @JsonProperty("signatureMethod")
    @NotNull
    private SignatureMethod signatureMethod;

    @JsonProperty("signatureFileStoreId")
    @NotBlank
    @Size(min = 1, max = 256)
    private String signatureFileStoreId;
}

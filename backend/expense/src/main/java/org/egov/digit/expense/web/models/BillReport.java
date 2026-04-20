package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.enums.ReportStatus;
import org.egov.digit.expense.web.models.enums.ReportType;
import org.springframework.validation.annotation.Validated;

@Schema(description = "A Object which holds the info about a generated bill report")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillReport {

    @JsonProperty("id")
    private String id;

    @JsonProperty("billId")
    @NotNull
    private String billId;

    @JsonProperty("tenantId")
    @NotNull
    private String tenantId;

    @JsonProperty("type")
    @NotNull
    private ReportType type;

    @JsonProperty("status")
    private ReportStatus status;

    @JsonProperty("fileStoreId")
    private String fileStoreId;

    @JsonProperty("errorDetails")
    private Object errorDetails;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails;
}

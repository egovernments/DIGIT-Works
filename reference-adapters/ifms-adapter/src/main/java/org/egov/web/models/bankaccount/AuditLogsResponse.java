package org.egov.web.models.bankaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;

import java.util.List;
@Schema(description = "Audit log response")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLogsResponse {
    @JsonProperty("responseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("AuditLogs")
    private List<Object> auditLogs;


}

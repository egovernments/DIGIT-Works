package org.egov.web.models.program;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.egov.web.models.ExchangeCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Program extends ExchangeCode {

    @JsonProperty("location_code")
    @NotNull
    @Size(min = 2, max = 64)
    private String locationCode;

    @JsonProperty("program_code")
    @Size(min = 2, max = 64)
    private String programCode;

    @JsonProperty("name")
    @NotNull
    @Size(min = 2, max = 64)
    private String name;

    @JsonProperty("parent_id")
    @Size(min = 2, max = 64)
    private String parentId;

    @JsonProperty("description")
    @Size(min = 2, max = 256)
    private String description;

    @JsonProperty("start_date")
    @NotNull
    private long startDate;

    @JsonProperty("end_date")
    private long endDate;

    @JsonProperty("client_host_url")
    @Size(min = 2, max = 128)
    private String clientHostUrl;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
}

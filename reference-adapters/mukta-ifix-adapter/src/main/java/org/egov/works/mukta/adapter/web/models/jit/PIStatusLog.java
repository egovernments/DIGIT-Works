package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.data.query.annotations.Exclude;
import org.egov.works.mukta.adapter.web.models.enums.JITServiceId;
import org.egov.works.mukta.adapter.web.models.enums.JitRespStatusForPI;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PIStatusLog {
    @JsonProperty("id")
    private String id;

    @JsonProperty("piId")
    private String piId;

    @JsonProperty("serviceId")
    private JITServiceId serviceId;

    @JsonProperty("status")
    private JitRespStatusForPI status;

    @JsonProperty("additionalDetails")
    private Object additionalDetails;

    @JsonProperty("auditDetails")
    @Exclude
    private AuditDetails auditDetails;
}

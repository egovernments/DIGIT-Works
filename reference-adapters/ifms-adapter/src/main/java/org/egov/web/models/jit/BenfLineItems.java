package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.data.query.annotations.Exclude;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BenfLineItems {
    @JsonProperty("id")
    private String id;

    @JsonProperty("beneficiaryId")
    private String beneficiaryId;

    @JsonProperty("lineItemId")
    private String lineItemId;

    @JsonProperty("auditDetails")
    @Exclude
    private AuditDetails auditDetails;

}

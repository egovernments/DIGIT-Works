package org.egov.wms.web.model.Job;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.services.common.models.expense.Pagination;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportSearchRequest {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("reportSearchCriteria")
    @Valid
    private ReportSearchCriteria reportSearchCriteria;

    @JsonProperty("pagination")
    private Pagination pagination;
}

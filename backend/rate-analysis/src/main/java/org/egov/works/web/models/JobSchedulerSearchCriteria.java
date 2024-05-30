package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

/**
 * JobSchedulerSearchCriteria
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobSchedulerSearchCriteria {
    @JsonProperty("requestInfo")

    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("searchCriteria")

    @Valid
    private SearchCriteria searchCriteria = null;

    @JsonProperty("pagination")

    @Valid
    private Pagination pagination = null;


}
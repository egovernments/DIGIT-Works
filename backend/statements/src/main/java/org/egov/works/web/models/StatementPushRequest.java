package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatementPushRequest {
    @JsonProperty("requestInfo")

    @Valid
    private RequestInfo requestInfo = null;
    @JsonProperty("statement")
    @Valid
    private Statement statement = null;
}

package org.egov.digit.expense.calculator.web.models.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Validated

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoundarySearchCriteria {

    @NotNull
    @Size(min = 1)
    @JsonProperty("codes")
    private List<String> codes;

    @NotNull
    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("limit")
    private Integer limit;

}

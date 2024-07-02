package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;


@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria {
    @JsonProperty("statementId")

    private List<String> statementId = null;

    @JsonProperty("tenantId")

    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("referenceId")
    @NotNull

    @Valid
    private String referenceId = null;

    @JsonProperty("statementType")
    private Statement.StatementTypeEnum statementType = null;



    public SearchCriteria addStatementIdItem(String statementIdItem) {
        if (this.statementId == null) {
            this.statementId = new ArrayList<>();
        }
        this.statementId.add(statementIdItem);
        return this;
    }

}

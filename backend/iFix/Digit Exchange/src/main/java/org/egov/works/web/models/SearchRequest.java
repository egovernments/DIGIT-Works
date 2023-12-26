package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Search disbursement requests
 */
@Schema(description = "Search disbursement requests")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-12-26T11:42:32.468+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {
    @JsonProperty("transaction_id")
    @NotNull

    @Size(max = 99)
    private String transactionId = null;

    @JsonProperty("search_criteria")
    @NotNull

    private List<Object> searchCriteria = new ArrayList<>();


    public SearchRequest addSearchCriteriaItem(Object searchCriteriaItem) {
        this.searchCriteria.add(searchCriteriaItem);
        return this;
    }

}

package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchCriteria
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria {
    @JsonProperty("tenantId")
    @NotNull
    private String tenantId = null;

    @JsonProperty("jobIds")

    private List<String> jobIds = null;

    @JsonProperty("ids")
    @Valid
    private List<String> ids = null;

    @JsonProperty("status")
    private StatusEnum status = null;

    @JsonProperty("effectiveFromDate")
    private Long effectiveFromDate = null;

    @JsonProperty("effectiveToDate")
    private Long effectiveToDate = null;


    public SearchCriteria addJobIdsItem(String jobIdsItem) {
        if (this.jobIds == null) {
            this.jobIds = new ArrayList<>();
        }
        this.jobIds.add(jobIdsItem);
        return this;
    }

}

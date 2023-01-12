package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * EstimateRequestWorkflow
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-07-27T14:01:35.043+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateRequestWorkflow {
    @JsonProperty("action")
    private String action = null;

    @JsonProperty("comment")
    private String comment = null;

    @JsonProperty("assignees")
    @Valid
    private List<String> assignees = null;

    public EstimateRequestWorkflow addAssigneesItem(String assigneesItem) {
        if (this.assignees == null) {
            this.assignees = new ArrayList<>();
        }
        this.assignees.add(assigneesItem);
        return this;
    }

}


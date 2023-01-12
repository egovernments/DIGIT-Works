package org.egov.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * Workflow
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Workflow   {
        @JsonProperty("action")
        private String action = null;

        @JsonProperty("comment")
        private String comment = null;

        @JsonProperty("assignees")
        @Valid
        private List<String> assignees = null;


        public Workflow addAssigneesItem(String assigneesItem) {
            if (this.assignees == null) {
            this.assignees = new ArrayList<>();
            }
        this.assignees.add(assigneesItem);
        return this;
        }

}


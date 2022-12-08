package org.egov.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.egov.web.models.ApiOperation;
import org.egov.web.models.ProjectBeneficiary;
import org.egov.web.models.RequestInfo;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * BeneficiaryRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeneficiaryRequest   {
        @JsonProperty("RequestInfo")
        private RequestInfo requestInfo = null;

        @JsonProperty("ProjectBeneficiary")
        @Valid
        private List<ProjectBeneficiary> projectBeneficiary = null;

        @JsonProperty("apiOperation")
        private ApiOperation apiOperation = null;


        public BeneficiaryRequest addProjectBeneficiaryItem(ProjectBeneficiary projectBeneficiaryItem) {
            if (this.projectBeneficiary == null) {
            this.projectBeneficiary = new ArrayList<>();
            }
        this.projectBeneficiary.add(projectBeneficiaryItem);
        return this;
        }

}


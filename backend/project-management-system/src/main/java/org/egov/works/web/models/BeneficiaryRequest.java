package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
public class BeneficiaryRequest {
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


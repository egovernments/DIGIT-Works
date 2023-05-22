package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * BeneficiaryResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeneficiaryResponse {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("ProjectBeneficiary")
    @Valid
    private List<ProjectBeneficiary> projectBeneficiary = null;


    public BeneficiaryResponse addProjectBeneficiaryItem(ProjectBeneficiary projectBeneficiaryItem) {
        if (this.projectBeneficiary == null) {
            this.projectBeneficiary = new ArrayList<>();
        }
        this.projectBeneficiary.add(projectBeneficiaryItem);
        return this;
    }

}


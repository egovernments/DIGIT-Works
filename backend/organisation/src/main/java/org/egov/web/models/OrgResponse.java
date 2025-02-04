package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * OrgResponse
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgResponse {

    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

    @JsonProperty("organisations")
    @Valid
    private List<Organisation> organisations = null;

    @JsonProperty("pagination")
    private Pagination pagination = null;

    public OrgResponse addOrganisationsItem(Organisation organisationsItem) {
        if (this.organisations == null) {
            this.organisations = new ArrayList<>();
        }
        this.organisations.add(organisationsItem);
        return this;
    }

}

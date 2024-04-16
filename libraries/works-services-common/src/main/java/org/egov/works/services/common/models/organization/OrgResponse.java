package org.egov.works.services.common.models.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

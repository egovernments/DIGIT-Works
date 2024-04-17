package org.egov.works.services.common.models.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgSearchCriteria {

    @JsonProperty("id")
    private List<String> id = null;

    @JsonProperty("tenantId")
    @Size(min = 2, max = 1000)
    private String tenantId = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("applicationNumber")
    private String applicationNumber = null;

    @JsonProperty("orgNumber")
    private String orgNumber = null;

    @JsonProperty("applicationStatus")
    private String applicationStatus = null;

    @JsonProperty("contactMobileNumber")
    private String contactMobileNumber = null;

    @JsonProperty("functions")
    private Function functions = null;

    @JsonProperty("createdFrom")
    private Long createdFrom = null;

    @JsonProperty("createdTo")
    private Long createdTo = null;

    @JsonProperty("boundaryCode")
    private String boundaryCode = null;

    @JsonProperty("identifierType")
    private String identifierType = null;

    @JsonProperty("identifierValue")
    private String identifierValue = null;

    @JsonProperty("includeDeleted")
    private Boolean includeDeleted = false;

    public OrgSearchCriteria addIdItem(String idItem) {
        if (this.id == null) {
            this.id = new ArrayList<>();
        }
        this.id.add(idItem);
        return this;
    }

}

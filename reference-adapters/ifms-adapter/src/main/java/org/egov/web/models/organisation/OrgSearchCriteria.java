package org.egov.web.models.organisation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * organisation search attributes
 */
@ApiModel(description = "organisation search attributes")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

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

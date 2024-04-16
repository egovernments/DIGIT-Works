package org.egov.works.services.common.models.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.Role;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDetails {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("individualId")
    private String individualId = null;

    @JsonProperty("orgId")
    private String orgId = null;

    @JsonProperty("contactName")
    @Size(min = 2, max = 64)
    private String contactName = null;

    @JsonProperty("contactMobileNumber")
    @Size(max = 20)
    private String contactMobileNumber = null;

    @JsonProperty("contactEmail")
    @Size(min = 5, max = 200)
    private String contactEmail = null;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("roles")
    @Valid
    private List<Role> roles;

    @Size(max=50)
    @JsonProperty("type")
    private String type;

    @Size(max=64)
    //@DiffIgnore
    @JsonProperty("createdBy")
    private String createdBy;

    //@DiffIgnore
    @JsonProperty("createdDate")
    private Long createdDate;

    @Size(max=64)
    //@DiffIgnore
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;

    //@DiffIgnore
    @JsonProperty("lastModifiedDate")
    private Long lastModifiedDate;

}

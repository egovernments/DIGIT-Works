package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgContactUpdateDiff {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    private String tenantId;

    private String organisationId;

    private Set<ContactDetails> oldContacts;

    private Set<ContactDetails> newContacts;

}

package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * User role carries the tenant related role information for the user. A user can have multiple roles per tenant based on the need of the tenant. A user may also have multiple roles for multiple tenants.
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantRole {
    @JsonProperty("tenantId")
    @NotNull

    private String tenantId = null;

    @JsonProperty("roles")
    @NotNull
    @Valid
    private List<Role> roles = new ArrayList<>();


    public TenantRole addRolesItem(Role rolesItem) {
        this.roles.add(rolesItem);
        return this;
    }

}

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
 * This is acting ID token of the authenticated user on the server. Any value provided by the clients will be ignored and actual user based on authtoken will be used on the server.
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
    @JsonProperty("tenantId")
    @NotNull

    private String tenantId = null;

    @JsonProperty("id")

    private Integer id = null;

    @JsonProperty("userName")
    @NotNull

    private String userName = null;

    @JsonProperty("mobile")

    private String mobile = null;

    @JsonProperty("email")

    private String email = null;

    @JsonProperty("primaryrole")
    @NotNull
    @Valid
    private List<Role> primaryrole = new ArrayList<>();

    @JsonProperty("additionalroles")
    @Valid
    private List<TenantRole> additionalroles = null;


    public UserInfo addPrimaryroleItem(Role primaryroleItem) {
        this.primaryrole.add(primaryroleItem);
        return this;
    }

    public UserInfo addAdditionalrolesItem(TenantRole additionalrolesItem) {
        if (this.additionalroles == null) {
            this.additionalroles = new ArrayList<>();
        }
        this.additionalroles.add(additionalrolesItem);
        return this;
    }

}

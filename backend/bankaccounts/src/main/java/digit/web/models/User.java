package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import digit.web.models.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * This is acting ID token of the authenticated user on the server. Any value provided by the clients will be ignored and actual user based on authtoken will be used on the server.
 */
@Schema(description = "This is acting ID token of the authenticated user on the server. Any value provided by the clients will be ignored and actual user based on authtoken will be used on the server.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User   {
        @JsonProperty("tenantId")
          @NotNull

                private String tenantId = null;

        @JsonProperty("id")

                private Integer id = null;

        @JsonProperty("uuid")

                private String uuid = null;

        @JsonProperty("userName")
          @NotNull

                private String userName = null;

        @JsonProperty("mobileNumber")

                private String mobileNumber = null;

        @JsonProperty("emailId")

                private String emailId = null;

        @JsonProperty("roles")
          @NotNull
          @Valid
                private List<Role> roles = new ArrayList<>();


        public User addRolesItem(Role rolesItem) {
        this.roles.add(rolesItem);
        return this;
        }

}

package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * StaffPermission
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T14:44:21.051+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffPermission {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("registerId")
    private String registerId = null;

    @JsonProperty("userId")
    private String userId = null;

    @JsonProperty("permissionLevels")
    @Valid
    private List<PermissionLevel> permissionLevels = null;

    @JsonProperty("enrollmentDate")
    private Double enrollmentDate = null;

    @JsonProperty("denrollmentDate")
    private Double denrollmentDate = null;


    public StaffPermission addPermissionLevelsItem(PermissionLevel permissionLevelsItem) {
        if (this.permissionLevels == null) {
            this.permissionLevels = new ArrayList<>();
        }
        this.permissionLevels.add(permissionLevelsItem);
        return this;
    }

}


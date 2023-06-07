package org.egov.web.models.individual;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.models.individual.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A representation of an Individual.
 */
@ApiModel(description = "A representation of an Individual.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-27T11:47:19.561+05:30")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Individual {

    @JsonProperty("id")
    @Size(min = 2, max = 64)
    private String id = null;

    @JsonProperty("individualId")
    @Size(min = 2, max = 64)
    private String individualId = null;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 1000)
    private String tenantId = null;

    @JsonProperty("clientReferenceId")
    @Size(min = 2, max = 64)
    private String clientReferenceId = null;

    @JsonProperty("userId")
    private String userId = null;

    @JsonProperty("name")
    @NotNull
    @Valid
    private Name name = null;

    @JsonProperty("dateOfBirth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth = null;

    @JsonProperty("gender")
    @Valid
    private Gender gender = null;

    @JsonProperty("bloodGroup")
    @Valid
    private BloodGroup bloodGroup = null;

    @JsonProperty("mobileNumber")
    @Size(max = 20)
    private String mobileNumber = null;

    @JsonProperty("altContactNumber")
    @Size(max = 16)
    private String altContactNumber = null;

    @JsonProperty("email")
    @Size(min = 5, max = 200)
    private String email = null;

    @JsonProperty("address")
    @Valid
    @Size(max = 3)
    private List<Address> address = null;

    @JsonProperty("fatherName")
    @Size(max = 100)
    private String fatherName = null;

    @JsonProperty("husbandName")
    @Size(max = 100)
    private String husbandName = null;

    @JsonProperty("relationship")
    @Size(max = 100)
    private String relationship = null;

    @JsonProperty("identifiers")
    @Valid
    private List<Identifier> identifiers = null;

    @JsonProperty("skills")
    @Valid
    private List<Skill> skills = null;

    @JsonProperty("photo")
    private String photo = null;

    @JsonProperty("additionalFields")
    @Valid
    private AdditionalFields additionalFields = null;

    @JsonProperty("isDeleted")
    private Boolean isDeleted = Boolean.FALSE;

    @JsonProperty("rowVersion")
    private Integer rowVersion = null;

    @JsonProperty("isSystemUser")
    private Boolean isSystemUser = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

    @JsonIgnore
    private Boolean hasErrors = Boolean.FALSE;

    @JsonProperty("userDetails")
    private UserDetails userDetails = null;



    public Individual addAddressItem(Address addressItem) {
        if (this.address == null) {
            this.address = new ArrayList<>();
        }
        this.address.add(addressItem);
        return this;
    }

    public Individual addIdentifiersItem(Identifier identifiersItem) {
        if (this.identifiers == null) {
            this.identifiers = new ArrayList<>();
        }
        this.identifiers.add(identifiersItem);
        return this;
    }

    public Individual addSkillsItem(Skill skillItem) {
        if (this.skills == null) {
            this.skills = new ArrayList<>();
        }
        this.skills.add(skillItem);
        return this;
    }

}


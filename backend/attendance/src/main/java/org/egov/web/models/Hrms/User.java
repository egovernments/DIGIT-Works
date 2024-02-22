package org.egov.web.models.Hrms;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.egov.common.models.core.Role;
import org.egov.common.models.user.GuardianRelation;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Validated
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Setter
@ToString
@Builder
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class User {
	
    @JsonProperty("id")
    private Long id;

    @Size(max=64)
    @JsonProperty("uuid")
    private String uuid;

    @Size(max=64)
    @JsonProperty("userServiceUuid")
    private String userServiceUuid;

    @Size(max=180)
    @JsonProperty("userName")
    private String userName;

    @Size(max=64)
    @JsonProperty("password")
    private String password;

    @Size(max = 5)
    @JsonProperty("salutation")
    private String salutation;

    @NotNull
    @Size(max=250)
    @JsonProperty("name")
    private String name;

    @JsonProperty("gender")
    private String gender;

    @Pattern(regexp = "^[0-9]{9,10}$", message = "MobileNumber should be either 9 or 10 digit number")
    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @Size(max=128)
    @JsonProperty("emailId")
    private String emailId;

    @Size(max=50)
    @JsonProperty("altContactNumber")
    private String altContactNumber;

    @Size(max=10)
    @JsonProperty("pan")
    private String pan;

    @Pattern(regexp = "^[0-9]{12}$", message = "AdharNumber should be 12 digit number")
    @JsonProperty("aadhaarNumber")
    private String aadhaarNumber;

    @Size(max=300)
    @JsonProperty("permanentAddress")
    private String permanentAddress;

    @Size(max=300)
    @JsonProperty("permanentCity")
    private String permanentCity;

    @Size(max=10)
    @JsonProperty("permanentPinCode")
    private String permanentPincode;

    @Size(max=300)
    @JsonProperty("correspondenceCity")
    private String correspondenceCity;

    @Size(max=10)
    @JsonProperty("correspondencePinCode")
    private String correspondencePincode;

    @Size(max=300)
    @JsonProperty("correspondenceAddress")
    private String correspondenceAddress;

    @JsonProperty("active")
    private Boolean active;

    @NotNull
    @JsonProperty("dob")
    private Long dob;

    @JsonProperty("pwdExpiryDate")
    private Long pwdExpiryDate;

    @Size(max=16)
    @JsonProperty("locale")
    private String locale;

    @Size(max=50)
    @JsonProperty("type")
    private String type;

    @Size(max=36)
    @JsonProperty("signature")
    private String signature;

    @JsonProperty("accountLocked")
    private Boolean accountLocked;

    @JsonProperty("roles")
    @Valid
    private List<Role> roles;

    @Size(max=100)
    @JsonProperty("fatherOrHusbandName")
    private String fatherOrHusbandName;

    @JsonProperty("relationship")
    private GuardianRelation relationship;

    @Size(max=32)
    @JsonProperty("bloodGroup")
    private String bloodGroup;

    @Size(max=300)
    @JsonProperty("identificationMark")
    private String identificationMark;

    @Size(max=36)
    @JsonProperty("photo")
    private String photo;

    @Size(max=64)
    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("createdDate")
    private Long createdDate;

    @Size(max=64)
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;

    @JsonProperty("lastModifiedDate")
    private Long lastModifiedDate;

    @JsonProperty("otpReference")
    private String otpReference;

    @Size(max=256)
    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("rowVersion")
    private Integer rowVersion;
    

}

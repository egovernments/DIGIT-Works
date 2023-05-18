package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Representation of a address. Individual APIs may choose to extend from this using allOf if more details needed to be added in their case.
 */
@ApiModel(description = "Representation of a address. Individual APIs may choose to extend from this using allOf if more details needed to be added in their case. ")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("doorNo")
    private String doorNo = null;

    @JsonProperty("latitude")
    private Double latitude = null;

    @JsonProperty("longitude")
    private Double longitude = null;

    @JsonProperty("locationAccuracy")
    private Double locationAccuracy = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("addressLine1")
    private String addressLine1 = null;

    @JsonProperty("addressLine2")
    private String addressLine2 = null;

    @JsonProperty("landmark")
    private String landmark = null;

    @JsonProperty("city")
    private String city = null;

    @JsonProperty("pincode")
    private String pincode = null;

    @JsonProperty("buildingName")
    private String buildingName = null;

    @JsonProperty("street")
    private String street = null;

    @JsonProperty("boundaryType")
    private String boundaryType = null;

    @JsonProperty("boundary")
    private String boundary = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

}


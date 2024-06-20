package org.egov.web.models.organisation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * Representation of a address. Indiavidual APIs may choose to extend from this using allOf if more details needed to be added in their case.
 */
@ApiModel(description = "Representation of a address. Indiavidual APIs may choose to extend from this using allOf if more details needed to be added in their case.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("orgId")
    private String orgId = null;

    @JsonProperty("doorNo")
    private String doorNo = null;

    @JsonProperty("plotNo")
    private String plotNo = null;

    @JsonProperty("landmark")
    private String landmark = null;

    @JsonProperty("city")
    private String city = null;

    @JsonProperty("pincode")
    private String pincode = null;

    @JsonProperty("district")
    private String district = null;

    @JsonProperty("region")
    private String region = null;

    @JsonProperty("state")
    private String state = null;

    @JsonProperty("country")
    private String country = null;

    @JsonProperty("additionDetails")
    private Object additionDetails = null;

    @JsonProperty("buildingName")
    private String buildingName = null;

    @JsonProperty("street")
    private String street = null;

    @JsonProperty("boundaryType")
    private String boundaryType = null;

    @JsonProperty("boundaryCode")
    private String boundaryCode = null;

//    @JsonProperty("boundary")
//    private Boundary boundary = null;

    @JsonProperty("geoLocation")
    private GeoLocation geoLocation = null;
}


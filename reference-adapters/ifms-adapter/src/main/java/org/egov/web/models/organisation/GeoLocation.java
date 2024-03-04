package org.egov.web.models.organisation;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeoLocation {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("addressId")
    private String addressId = null;

    @JsonProperty("latitude")
    private Double latitude = null;

    @JsonProperty("longitude")
    private Double longitude = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

}

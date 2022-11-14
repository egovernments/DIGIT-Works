package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Contains information about the device used to access the api
 */
@ApiModel(description = "Contains information about the device used to access the api")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T14:44:21.051+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDetail {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("signature")
    private String signature = null;


}


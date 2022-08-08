package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Info of the API being called
 */
@ApiModel(description = "Info of the API being called")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-07-27T14:01:35.043+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIInfo {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("version")
    private String version = null;

    @JsonProperty("path")
    private String path = null;

}


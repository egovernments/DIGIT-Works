package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Info of the API being called
 */
@ApiModel(description = "Info of the API being called")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-08-04T15:05:28.525+05:30")

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


package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Request for LetterOfIndent _create and _update api&#39;s
 */
@ApiModel(description = "Request for LetterOfIndent _create and _update api's")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-08-04T15:05:28.525+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LetterOfIndentRequest {
    @JsonProperty("requestInfo")
    private RequestHeader requestInfo = null;

    @JsonProperty("letterOfIndent")
    private LetterOfIndent letterOfIndent = null;

    @JsonProperty("workflow")
    private LetterOfIndentRequestWorkflow workflow = null;


}


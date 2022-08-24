package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * SaveLetterOfIndent
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-08-04T15:05:28.525+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveLetterOfIndent   {
        @JsonProperty("requestInfo")
        private RequestHeader requestInfo = null;

        @JsonProperty("letterOfIndent")
        private LetterOfIndent letterOfIndent = null;

        @JsonProperty("workflow")
        private LetterOfIndentRequestWorkflow workflow = null;


}


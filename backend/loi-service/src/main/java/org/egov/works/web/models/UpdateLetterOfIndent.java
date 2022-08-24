package org.egov.works.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.egov.works.web.models.LetterOfIndent;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.egov.works.web.models.LetterOfIndentRequestWorkflow;
import org.egov.works.web.models.RequestHeader;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * UpdateLetterOfIndent
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-08-04T15:05:28.525+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateLetterOfIndent   {
        @JsonProperty("requestInfo")
        private RequestHeader requestInfo = null;

        @JsonProperty("letterOfIndent")
        private LetterOfIndent letterOfIndent = null;

        @JsonProperty("workflow")
        private LetterOfIndentRequestWorkflow workflow = null;


}


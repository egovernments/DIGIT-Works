package org.egov.works.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.egov.works.web.models.LetterOfIndent;
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
 * Response for the LetterOfIndent _create, _update and _search api&#39;s
 */
@ApiModel(description = "Response for the LetterOfIndent _create, _update and _search api's")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-08-04T15:05:28.525+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LetterOfIndentResponse   {
        @JsonProperty("responseInfo")
        private RequestHeader responseInfo = null;

        @JsonProperty("letterOfIndents")
        @Valid
        private List<LetterOfIndent> letterOfIndents = new ArrayList<>();


        public LetterOfIndentResponse addLetterOfIndentsItem(LetterOfIndent letterOfIndentsItem) {
        this.letterOfIndents.add(letterOfIndentsItem);
        return this;
        }

}


package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
public class LetterOfIndentResponse {
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

    @JsonProperty("letterOfIndents")
    @Valid
    private List<LetterOfIndent> letterOfIndents = new ArrayList<>();


    public LetterOfIndentResponse addLetterOfIndentsItem(LetterOfIndent letterOfIndentsItem) {
        this.letterOfIndents.add(letterOfIndentsItem);
        return this;
    }

}


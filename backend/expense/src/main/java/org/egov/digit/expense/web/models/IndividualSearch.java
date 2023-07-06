package org.egov.digit.expense.web.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * A representation of an Individual.
 */
@ApiModel(description = "A representation of an Individual.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-07-06T13:20:30.211+05:30")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualSearch {

    @JsonProperty("id")
    private List<String> id = null;

}

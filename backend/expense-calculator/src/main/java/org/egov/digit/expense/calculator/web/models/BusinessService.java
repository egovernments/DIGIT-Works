package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessService {

    @JsonProperty("businessService")
    private String businessService = null;

    @JsonProperty("code")
    private String code = null;
}

package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Schema(description = "A Object which holds the wage seeker skill")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WageSeekerSkill {
    @JsonProperty("name")
    @Valid
    private String name = null;

    @JsonProperty("code")
    @Valid
    private String code = null;

    @JsonProperty("active")
    @Valid
    private Boolean active = null;

    @JsonProperty("amount")
    @Valid
    private Double amount = null;
}

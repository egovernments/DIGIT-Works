package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Payer")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payer {
    @JsonProperty("id")
    @Valid
    private String id;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId;

    @JsonProperty("code")
    @Valid
    private String code;

    @JsonProperty("type")
    @NotNull
    @Size(min = 2, max = 64)
    private String type;

    @JsonProperty("active")
    @Size(min = 2, max = 64)
    private Boolean status;

}

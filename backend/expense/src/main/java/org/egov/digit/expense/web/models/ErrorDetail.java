package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Error detail object
 */
@Schema(description = "Error detail object")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetail {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;
}

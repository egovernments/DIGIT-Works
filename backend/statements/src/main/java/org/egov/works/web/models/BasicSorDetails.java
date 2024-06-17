package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/**
 * BasicSorDetails
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicSorDetails {
    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("amount")

    @Valid
    private BigDecimal amount = null;

    @JsonProperty("type")

    private String type = null;

    @JsonProperty("quantity")

    @Valid
    private BigDecimal quantity = null;






}

package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

/**
 * Object to capture bank branch identifier for a banking org to be used for wire transfers.
 */
@Schema(description = "Object to capture bank branch identifier for a banking org to be used for wire transfers.")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankBranchIdentifier {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("type")
    @NotNull
    @Size(min = 2, max = 64)
    private String type = null;

    @JsonProperty("code")
    @NotNull
    @Size(min = 2, max = 64)
    private String code = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


}

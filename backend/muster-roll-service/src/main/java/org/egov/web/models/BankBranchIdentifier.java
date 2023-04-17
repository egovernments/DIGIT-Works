package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

/**
 * Object to capture bank branch identifier for a banking org to be used for wire transfers.
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankBranchIdentifier {

    @JsonProperty("type")
    @Size(min = 2, max = 64)
    private String type = null;

    @JsonProperty("code")
    @Size(min = 2, max = 64)
    private String code = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


}

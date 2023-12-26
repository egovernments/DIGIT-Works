package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * DIGIT fiscal information exchange specification
 */
@Schema(description = "DIGIT fiscal information exchange specification")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-12-26T11:42:32.468+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FiscalData {
    @JsonProperty("function")

    private String function = null;

    @JsonProperty("administration")

    private String administration = null;

    @JsonProperty("location")

    private String location = null;

    @JsonProperty("programs")

    private String programs = null;

    @JsonProperty("recipient_segment")

    private String recipientSegment = null;

    @JsonProperty("econimic_segment")

    private String econimicSegment = null;

    @JsonProperty("source_of_fund")

    private String sourceOfFund = null;

    @JsonProperty("target_segment")

    private String targetSegment = null;


}

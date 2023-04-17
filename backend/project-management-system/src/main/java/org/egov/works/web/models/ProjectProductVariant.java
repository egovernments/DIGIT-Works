package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * ProjectProductVariant
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectProductVariant {
    @JsonProperty("productVariantId")
    private String productVariantId = null;

    @JsonProperty("isBaseUnitVariant")
    private Boolean isBaseUnitVariant = null;


}


package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Collection of audit related fields used by most models
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDetails {
    @JsonProperty("createdBy")

    private String createdBy = null;

    @JsonProperty("lastModifiedBy")

    private String lastModifiedBy = null;

    @JsonProperty("createdTime")

    private Long createdTime = null;

    @JsonProperty("lastModifiedTime")

    private Long lastModifiedTime = null;


}

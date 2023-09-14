package org.egov.works.measurement.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * Collection of audit related fields used by most models
 */
@Schema(description = "Collection of audit related fields used by most models")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDetails   {
        @JsonProperty("createdBy")

                private String createdBy = null;

        @JsonProperty("lastModifiedBy")

                private String lastModifiedBy = null;

        @JsonProperty("createdTime")

                private Long createdTime = null;

        @JsonProperty("lastModifiedTime")

                private Long lastModifiedTime = null;


}

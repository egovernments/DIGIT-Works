package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

/**
 * JobScheduler
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobScheduler {
    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("tenantId")
    @Size(min = 2, max = 64)
    @NotNull
    private String tenantId = null;

    @JsonProperty("effectiveFrom")
    @NotNull
    @Valid
    private Long effectiveFrom = null;

    @JsonProperty("sorIds")
    private Set<String> sorIds = null;
}
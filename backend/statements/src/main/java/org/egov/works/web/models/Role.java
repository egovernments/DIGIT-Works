package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @JsonProperty("name")
    @NotNull

    @Size(max = 64)
    private String name = null;

    @JsonProperty("description")

    private String description = null;


}

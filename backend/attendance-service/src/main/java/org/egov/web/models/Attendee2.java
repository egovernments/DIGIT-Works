package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Attendee2
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T14:44:21.051+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attendee2 {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("registerId")
    private UUID registerId = null;

    @JsonProperty("individualId")
    private UUID individualId = null;

    @JsonProperty("enrollmentDate")
    private Double enrollmentDate = null;

    @JsonProperty("denrollmentDate")
    private Double denrollmentDate = null;


}


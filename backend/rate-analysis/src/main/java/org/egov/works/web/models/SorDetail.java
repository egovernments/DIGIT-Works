package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * SorDetail
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SorDetail {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("sorId")

    private String sorId = null;

    @JsonProperty("sorCode")

    private String sorCode = null;

    @JsonProperty("status")

    private StatusEnum status = null;

    @JsonProperty("failureReason")

    private String failureReason = null;

    @JsonProperty("additionalDetails")

    private Object additionalDetails = null;


}

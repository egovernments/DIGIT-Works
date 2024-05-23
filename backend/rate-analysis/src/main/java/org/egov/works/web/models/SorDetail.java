package org.egov.works.web.models;

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
 * SorDetail
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SorDetail   {
        @JsonProperty("sorId")

                private String sorId = null;

        @JsonProperty("sorCode")

                private String sorCode = null;

        @JsonProperty("status")

                private String status = null;

        @JsonProperty("failureReason")

                private String failureReason = null;

        @JsonProperty("additionalDetails")

                private Object additionalDetails = null;


}

package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * All APIs will return ErrorRes in case of failure which will carry ResponseInfo as metadata and Error object as actual representation of error. In case of bulk apis, some apis may chose to return the array of Error objects to indicate individual failure.
 */
@Schema(description = "All APIs will return ErrorRes in case of failure which will carry ResponseInfo as metadata and Error object as actual representation of error. In case of bulk apis, some apis may chose to return the array of Error objects to indicate individual failure.")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-12-21T10:35:43.292+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorRes {
    @JsonProperty("message")
    private String message;

    @JsonProperty("objects")
    private List<Object> objects;
}

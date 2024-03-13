package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Error object will be returned as a part of reponse body in conjunction with ResponseInfo as part of ErrorResponse whenever the request processing status in the ResponseInfo is FAILED. HTTP return in this scenario will usually be HTTP 400.
 */
@Schema(description = "Error object will be returned as a part of reponse body in conjunction with ResponseInfo as part of ErrorResponse whenever the request processing status in the ResponseInfo is FAILED. HTTP return in this scenario will usually be HTTP 400.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-12-21T10:35:43.292+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
    @JsonProperty("code")
    @NotNull

    private String code = null;

    @JsonProperty("message")
    @NotNull

    private String message = null;

    @JsonProperty("description")

    private String description = null;

    @JsonProperty("params")

    private List<String> params = null;


    public Error addParamsItem(String paramsItem) {
        if (this.params == null) {
            this.params = new ArrayList<>();
        }
        this.params.add(paramsItem);
        return this;
    }

}

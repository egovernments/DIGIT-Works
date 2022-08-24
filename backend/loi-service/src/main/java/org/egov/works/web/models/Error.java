package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * Error object will be returned as a part of reponse body in conjunction with ResponseHeader as part of ErrorResponse whenever the request processing status in the ResponseHeader is FAILED. HTTP return in this scenario will usually be HTTP 400.
 */
@ApiModel(description = "Error object will be returned as a part of reponse body in conjunction with ResponseHeader as part of ErrorResponse whenever the request processing status in the ResponseHeader is FAILED. HTTP return in this scenario will usually be HTTP 400.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-08-04T15:05:28.525+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error   {
        @JsonProperty("code")
        private String code = null;

        @JsonProperty("message")
        private String message = null;

        @JsonProperty("description")
        private String description = null;

        @JsonProperty("params")
        @Valid
        private List<String> params = null;


        public Error addParamsItem(String paramsItem) {
            if (this.params == null) {
            this.params = new ArrayList<>();
            }
        this.params.add(paramsItem);
        return this;
        }

}


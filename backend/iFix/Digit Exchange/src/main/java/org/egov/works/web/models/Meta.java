package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @context: \&quot;https://example.org/schema/Meta\&quot; &lt;br&gt; @type: \&quot;@context\&quot; &lt;br&gt;    **Notes:**   1. Additional meta info defined as per implementation context.   2. Usually list of name/value, tags, etc., to provide additional info.   3. The information SHOULD be privacy preserving attributes/values when passed in the protocol header.
 */
@Schema(description = "@context: \"https://example.org/schema/Meta\" <br> @type: \"@context\" <br>    **Notes:**   1. Additional meta info defined as per implementation context.   2. Usually list of name/value, tags, etc., to provide additional info.   3. The information SHOULD be privacy preserving attributes/values when passed in the protocol header. ")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-12-26T11:42:32.468+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Meta {
    @JsonProperty("tenantId")
    @NotNull

    private String tenantId = null;

    @JsonProperty("fiscal")

    @Valid
    private FiscalData fiscal = null;


}

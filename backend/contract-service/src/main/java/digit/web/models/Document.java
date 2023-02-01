package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * This object holds list of documents attached during the transaciton for a property
 */
@ApiModel(description = "This object holds list of documents attached during the transaciton for a property")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document   {
        @JsonProperty("id")
        
@Size(max=64) 

        private String id = null;

        @JsonProperty("documentType")
        


        private String documentType = null;

        @JsonProperty("fileStore")
        


        private String fileStore = null;

        @JsonProperty("documentUid")
        
@Size(max=64) 

        private String documentUid = null;

        @JsonProperty("additionalDetails")
        


        private Object additionalDetails = null;


}


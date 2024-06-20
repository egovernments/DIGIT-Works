package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Sor {



    @JsonProperty("id")
    @Valid
    private String id = null;
    @JsonProperty("uom")
    @Valid
     private String uom=null;
    @JsonProperty("sorType")
    @Valid
    private String sorType= null;
    @JsonProperty("quantity")
    @Valid
    BigDecimal quantity= null;
    @JsonProperty("sorSubType")
    @Valid
    private String sorSubType=null;
    @JsonProperty("sorVariant")
    @Valid
    private String sorVariant= null;
    @JsonProperty("description")
    @Valid
    private String description= null;




}

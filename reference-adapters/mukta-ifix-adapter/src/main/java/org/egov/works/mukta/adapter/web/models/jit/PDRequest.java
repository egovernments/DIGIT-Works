package org.egov.works.mukta.adapter.web.models.jit;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PDRequest {

    @JsonProperty("finYear")
    private String finYear;

    @JsonProperty("extAppName")
    private String extAppName;

    @JsonProperty("billRefNo")
    private String billRefNo;

    @JsonProperty("tokenNumber")
    private String tokenNumber;

    @JsonProperty("tokenDate")
    private String tokenDate;

}

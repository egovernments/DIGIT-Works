package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VARequest {
    @JsonProperty("hoa")
    private String hoa;

    @JsonProperty("fromDate")
    private String fromDate;

    @JsonProperty("ddoCode")
    private String ddoCode;

    @JsonProperty("granteCode")
    private String granteCode;

}

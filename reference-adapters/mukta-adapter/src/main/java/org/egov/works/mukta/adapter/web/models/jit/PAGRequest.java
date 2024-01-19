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
public class PAGRequest {

    @JsonProperty("pmtInstId")
    private String pmtInstId;

    @JsonProperty("pmtInstDate")
    private String pmtInstDate;

    @JsonProperty("billNo")
    private String billNo;

    @JsonProperty("billDate")
    private String billDate;

    @JsonProperty("ssuIaId")
    private String ssuIaId;

}

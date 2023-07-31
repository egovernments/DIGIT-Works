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
public class PISRequest {

    @JsonProperty("jitBillNo")
    private String jitBillNo;

    @JsonProperty("jitBillDate")
    private String jitBillDate;

    @JsonProperty("ssuIaId")
    private String ssuIaId;

}

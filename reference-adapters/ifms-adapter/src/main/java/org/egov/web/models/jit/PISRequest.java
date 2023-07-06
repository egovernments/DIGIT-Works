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
    String jitBillNo;

    @JsonProperty("jitBillDate")
    String jitBillDate;

    @JsonProperty("ssuIaId")
    String ssuIaId;

}

package org.egov.web.models.disburse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.web.models.Disbursement;
import org.egov.web.models.MsgCallbackHeader;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisburseSearchResponse {

    @JsonProperty("header")
    @NotNull
    private MsgCallbackHeader header;

    @JsonProperty("disbursements")
    private List<Disbursement> disbursements;

}

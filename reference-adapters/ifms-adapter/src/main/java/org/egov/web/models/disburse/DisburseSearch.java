package org.egov.web.models.disburse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.web.models.Pagination;
import org.egov.web.models.PaginationForDisburse;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisburseSearch {


    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("location_code")
    @NotNull
    private String locationCode;

    @JsonProperty("program_code")
    private String programCode;

    @JsonProperty("target_id")
    private String targetId;

    @JsonProperty("pagination")
    private PaginationForDisburse pagination;

}

package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Schema(description = "Search criteria for bill report")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillReportSearchCriteria {

    @JsonProperty("billId")
    @NotNull
    private String billId;

    @JsonProperty("tenantId")
    @NotNull
    private String tenantId;

    @JsonProperty("type")
    private String type;
}

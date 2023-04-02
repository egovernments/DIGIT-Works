package org.egov.digit.expense.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.Pagination;
import org.egov.digit.expense.web.models.RequestInfo;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * BillSearchRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillSearchRequest   {
        @JsonProperty("requestInfo")

          @Valid
                private RequestInfo requestInfo = null;

        @JsonProperty("demandCriteria")

          @Valid
                private BillCriteria demandCriteria = null;

        @JsonProperty("pagination")

          @Valid
                private Pagination pagination = null;


}

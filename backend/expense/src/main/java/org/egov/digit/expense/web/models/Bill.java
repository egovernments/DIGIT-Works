package org.egov.digit.expense.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.egov.digit.expense.web.models.AuditDetails;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.Party;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * A Object which holds the info about the expense details
 */
@Schema(description = "A Object which holds the info about the expense details")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill   {
        @JsonProperty("id")

          @Valid
                private UUID id = null;

        @JsonProperty("tenantId")
          @NotNull

        @Size(min=2,max=64)         private String tenantId = null;

        @JsonProperty("billDate")

          @Valid
                private BigDecimal billDate = null;

        @JsonProperty("dueDate")

          @Valid
                private BigDecimal dueDate = null;

        @JsonProperty("netPayableAmount")

          @Valid
                private BigDecimal netPayableAmount = null;

        @JsonProperty("netPaidAmount")

          @Valid
                private BigDecimal netPaidAmount = null;

        @JsonProperty("businessService")
          @NotNull

        @Size(min=2,max=64)         private String businessService = null;

        @JsonProperty("referenceId")

        @Size(min=2,max=64)         private String referenceId = null;

        @JsonProperty("fromPeriod")

          @Valid
                private BigDecimal fromPeriod = null;

        @JsonProperty("toPeriod")

          @Valid
                private BigDecimal toPeriod = null;

        @JsonProperty("paymentStatus")

        @Size(min=2,max=64)         private String paymentStatus = null;

        @JsonProperty("wfStatus")

        @Size(min=2,max=64)         private String wfStatus = null;

        @JsonProperty("payer")
          @NotNull

          @Valid
                private Party payer = null;

        @JsonProperty("billDetails")
          @NotNull
          @Valid
                private List<BillDetail> billDetails = new ArrayList<>();

        @JsonProperty("additionalFields")

                private Object additionalFields = null;

        @JsonProperty("auditDetails")

          @Valid
                private AuditDetails auditDetails = null;


        public Bill addBillDetailsItem(BillDetail billDetailsItem) {
        this.billDetails.add(billDetailsItem);
        return this;
        }

}

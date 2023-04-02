package org.egov.digit.expense.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.egov.digit.expense.web.models.LineItems;
import org.egov.digit.expense.web.models.Party;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * Bill details of the individual payees
 */
@Schema(description = "Bill details of the individual payees")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetail   {
        @JsonProperty("id")

          @Valid
                private UUID id = null;

        @JsonProperty("referenceId")

        @Size(min=2,max=64)         private String referenceId = null;

        @JsonProperty("paymentStatus")

        @Size(min=2,max=64)         private String paymentStatus = null;

        @JsonProperty("fromPeriod")

          @Valid
                private BigDecimal fromPeriod = null;

        @JsonProperty("toPeriod")

          @Valid
                private BigDecimal toPeriod = null;

        @JsonProperty("payee")
          @NotNull

          @Valid
                private Party payee = null;

        @JsonProperty("lineItems")
          @Valid
                private List<LineItems> lineItems = null;

        @JsonProperty("payableLineItem")
          @NotNull
          @Valid
                private List<LineItems> payableLineItem = new ArrayList<>();


        public BillDetail addLineItemsItem(LineItems lineItemsItem) {
            if (this.lineItems == null) {
            this.lineItems = new ArrayList<>();
            }
        this.lineItems.add(lineItemsItem);
        return this;
        }

        public BillDetail addPayableLineItemItem(LineItems payableLineItemItem) {
        this.payableLineItem.add(payableLineItemItem);
        return this;
        }

}

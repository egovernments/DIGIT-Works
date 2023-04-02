package org.egov.digit.expense.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.Pagination;
import org.egov.digit.expense.web.models.ResponseInfo;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * BillResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillResponse   {
        @JsonProperty("responseInfo")

          @Valid
                private ResponseInfo responseInfo = null;

        @JsonProperty("bill")
          @Valid
                private List<Bill> bill = null;

        @JsonProperty("pagination")

          @Valid
                private Pagination pagination = null;


        public BillResponse addBillItem(Bill billItem) {
            if (this.bill == null) {
            this.bill = new ArrayList<>();
            }
        this.bill.add(billItem);
        return this;
        }

}

package org.egov.digit.expense.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;
import org.egov.digit.expense.web.models.AuditDetails;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * Line items are the amount breakups for net amounts
 */
@Schema(description = "Line items are the amount breakups for net amounts")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LineItems   {
        @JsonProperty("id")

          @Valid
                private UUID id = null;

        @JsonProperty("tenantId")
          @NotNull

        @Size(min=2,max=64)         private String tenantId = null;

        @JsonProperty("headCode")
          @NotNull

        @Size(min=2,max=64)         private String headCode = null;

        @JsonProperty("amount")
          @NotNull

          @Valid
                private BigDecimal amount = null;

            /**
            * Type of line item
            */
            public enum TypeEnum {
                        PAYABLE("PAYABLE"),
                        
                        DEDUCTION("DEDUCTION");
            
            private String value;
            
            TypeEnum(String value) {
            this.value = value;
            }
            
            @Override
            @JsonValue
            public String toString() {
            return String.valueOf(value);
            }
            
            @JsonCreator
            public static TypeEnum fromValue(String text) {
            for (TypeEnum b : TypeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
            return b;
            }
            }
            return null;
            }
            }        @JsonProperty("type")
          @NotNull

                private TypeEnum type = null;

        @JsonProperty("paidAmount")

          @Valid
                private BigDecimal paidAmount = null;

        @JsonProperty("isActive")

                private Boolean isActive = null;

        @JsonProperty("additionalFields")

                private Object additionalFields = null;

        @JsonProperty("auditDetails")

          @Valid
                private AuditDetails auditDetails = null;


}

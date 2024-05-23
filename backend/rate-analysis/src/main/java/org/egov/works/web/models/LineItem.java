package org.egov.works.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.egov.works.web.models.AmountDetail;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * LineItem
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LineItem   {
        @JsonProperty("id")

          @Valid
                private UUID id = null;

            /**
            * Sors or ExtraCharges
            */
            public enum TypeEnum {
                        SOR("SOR"),
                        
                        NONSOR("NONSOR"),
                        
                        EXTRACHARGES("EXTRACHARGES");
            
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

                private TypeEnum type = null;

        @JsonProperty("targetId")

                private String targetId = null;

        @JsonProperty("amountDetails")
          @Valid
                private List<AmountDetail> amountDetails = null;

        @JsonProperty("additionalDetials")

                private Object additionalDetials = null;


        public LineItem addAmountDetailsItem(AmountDetail amountDetailsItem) {
            if (this.amountDetails == null) {
            this.amountDetails = new ArrayList<>();
            }
        this.amountDetails.add(amountDetailsItem);
        return this;
        }

}

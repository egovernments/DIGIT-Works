package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * LineItem
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LineItem {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("type")
    private TypeEnum type = null;

    @JsonProperty("targetId")
    private String targetId = null;

    @JsonProperty("amountDetails")
    @Valid
    private List<BasicSorDetails> amountDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    public LineItem addAmountDetailsItem(BasicSorDetails amountDetailsItem) {
        if (this.amountDetails == null) {
            this.amountDetails = new ArrayList<>();
        }
        this.amountDetails.add(amountDetailsItem);
        return this;
    }


    /**
     * Sors or ExtraCharges
     */
    public enum TypeEnum {
        SOR("SOR"),

        NONSOR("NONSOR"),

        EXTRACHARGES("EXTRACHARGES");

        private final String value;

        TypeEnum(String value) {
            this.value = value;
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

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }

}

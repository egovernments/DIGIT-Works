package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;


/**
 * BasicSor
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicSor {
    @JsonProperty("id")

    @Size(min = 2, max = 64)
    private String id = null;

    @JsonProperty("sorId")

    @Size(min = 2, max = 64)
    private String sorId = null;

    /**
     * Type of basic SOR
     */
   /* public enum SorTypeEnum {
        LABOUR("Labour"),

        MACHINERY("Machinery"),

        MATERIAL("Material");

        private String value;

        SorTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static SorTypeEnum fromValue(String text) {
            for (SorTypeEnum b : SorTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }*/

    @JsonProperty("sorType")

    private String sorType = null;
    @JsonProperty("referenceId")
    private String referenceId=null;


    @JsonProperty("basicSorDetails")
    @Valid
    private List<BasicSorDetails> basicSorDetails = null;

    @JsonProperty("additionalDetails")

    private Object additionalDetails = null;


    public BasicSor addBasicSorDetailsItem(BasicSorDetails basicSorDetailsItem) {
        if (this.basicSorDetails == null) {
            this.basicSorDetails = new ArrayList<>();
        }
        this.basicSorDetails.add(basicSorDetailsItem);
        return this;
    }

}

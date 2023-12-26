package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a address. Indiavidual APIs may choose to extend from this using allOf if more details needed to be added in their case.
 */
@Schema(description = "Representation of a address. Indiavidual APIs may choose to extend from this using allOf if more details needed to be added in their case. ")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-12-21T10:35:43.292+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @JsonProperty("tenantId")

    private String tenantId = null;

    @JsonProperty("doorNo")

    private String doorNo = null;

    @JsonProperty("latitude")

    private Double latitude = null;

    @JsonProperty("longitude")

    private Double longitude = null;

    @JsonProperty("addressId")

    private String addressId = null;

    @JsonProperty("addressNumber")

    private String addressNumber = null;
    @JsonProperty("type")

    private List<TypeEnum> type = null;
    @JsonProperty("addressLine1")

    private String addressLine1 = null;
    @JsonProperty("addressLine2")

    private String addressLine2 = null;
    @JsonProperty("landmark")

    private String landmark = null;
    @JsonProperty("city")

    private String city = null;
    @JsonProperty("pincode")

    private String pincode = null;
    @JsonProperty("detail")

    private String detail = null;
    @JsonProperty("buildingName")

    @Size(min = 2, max = 64)
    private String buildingName = null;
    @JsonProperty("street")

    @Size(min = 2, max = 64)
    private String street = null;
    @JsonProperty("boundary")

    @Valid
    private Boundary boundary = null;
    @JsonProperty("boundaryType")

    private String boundaryType = null;

    public Address addTypeItem(TypeEnum typeItem) {
        if (this.type == null) {
            this.type = new ArrayList<>();
        }
        this.type.add(typeItem);
        return this;
    }


    /**
     * Gets or Sets type
     */
    public enum TypeEnum {
        PERMANENT("PERMANENT"),

        CORRESPONDENCE("CORRESPONDENCE");

        private String value;

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

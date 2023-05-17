package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Facility
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Facility {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("isPermanent")
    private Boolean isPermanent = true;

    /**
     * Gets or Sets usage
     */
    public enum UsageEnum {
        STORAGE_WAREHOUSE("STORAGE_WAREHOUSE"),

        MEDICAL_FACILITY("MEDICAL_FACILITY"),

        SEWAGE_TREATMENT_PLANT("SEWAGE_TREATMENT_PLANT");

        private String value;

        UsageEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static UsageEnum fromValue(String text) {
            for (UsageEnum b : UsageEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("usage")
    private UsageEnum usage = null;

    @JsonProperty("storageCapacity")
    private Integer storageCapacity = null;

    @JsonProperty("address")
    private Address address = null;

    @JsonProperty("additionalFields")
    private AdditionalFields additionalFields = null;

    @JsonProperty("isDeleted")
    private Boolean isDeleted = null;

    @JsonProperty("rowVersion")
    private Integer rowVersion = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;


}


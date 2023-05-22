package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * StockTransaction
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockTransaction {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("clientReferenceId")
    private String clientReferenceId = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("facilityId")
    private String facilityId = null;

    @JsonProperty("productVariantId")
    private String productVariantId = null;

    @JsonProperty("quantity")
    private Long quantity = null;

    @JsonProperty("referenceId")
    private String referenceId = null;

    @JsonProperty("referenceIdType")
    private String referenceIdType = null;

    /**
     * Gets or Sets transactionType
     */
    public enum TransactionTypeEnum {
        RECEIVED("RECEIVED"),

        DISPATCHED("DISPATCHED");

        private String value;

        TransactionTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static TransactionTypeEnum fromValue(String text) {
            for (TransactionTypeEnum b : TransactionTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("transactionType")
    private TransactionTypeEnum transactionType = null;

    /**
     * Gets or Sets transactionReason
     */
    public enum TransactionReasonEnum {
        RECEIVED("RECEIVED"),

        RETURNED("RETURNED"),

        LOSS("LOSS");

        private String value;

        TransactionReasonEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static TransactionReasonEnum fromValue(String text) {
            for (TransactionReasonEnum b : TransactionReasonEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("transactionReason")
    private TransactionReasonEnum transactionReason = null;

    @JsonProperty("transactingPartyId")
    private String transactingPartyId = null;

    @JsonProperty("transactingPartyType")
    private String transactingPartyType = null;

    @JsonProperty("additionalFields")
    private AdditionalFields additionalFields = null;

    @JsonProperty("isDeleted")
    private Boolean isDeleted = null;

    @JsonProperty("rowVersion")
    private Integer rowVersion = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;


}


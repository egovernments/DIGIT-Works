package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import digit.models.coremodels.AuditDetails;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Contract details
 */
@ApiModel(description = "Contract details")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contract {
    @JsonProperty("id")
    @Valid
    private UUID id = null;

    @JsonProperty("contractNumber")
    @Size(min = 1, max = 64)
    private String contractNumber = null;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("wfStatus")
    @Size(min = 2, max = 64)
    private String wfStatus = null;

    @JsonProperty("executingAuthority")
    @NotNull
    private ExecutingAuthorityEnum executingAuthority = null;

    @JsonProperty("contractType")
    private ContractTypeEnum contractType = null;

    @JsonProperty("totalContractedamount")
    @Valid
    private BigDecimal totalContractedamount = null;

    @JsonProperty("securityDeposit")
    @Valid
    private BigDecimal securityDeposit = null;

    @JsonProperty("agreementDate")
    @Valid
    private BigDecimal agreementDate = null;

    @JsonProperty("defectLiabilityPeriod")
    @Valid
    private BigDecimal defectLiabilityPeriod = null;

    @JsonProperty("orgId")
    private String orgId = null;

    @JsonProperty("startDate")
    @Valid
    private BigDecimal startDate = null;

    @JsonProperty("endDate")
    @Valid
    private BigDecimal endDate = null;
    @JsonProperty("lineItems")
    @Valid
    private List<LineItems> lineItems = null;
    @JsonProperty("documents")
    @Valid
    private List<Document> documents = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    public Contract addLineItemsItem(LineItems lineItemsItem) {
        if (this.lineItems == null) {
            this.lineItems = new ArrayList<>();
        }
        this.lineItems.add(lineItemsItem);
        return this;
    }

    public Contract addDocumentsItem(Document documentsItem) {
        if (this.documents == null) {
            this.documents = new ArrayList<>();
        }
        this.documents.add(documentsItem);
        return this;
    }


    /**
     * The executing authority of the given contract
     */
    public enum ExecutingAuthorityEnum {
        DEPARTMENT("DEPARTMENT"),

        CONTRACTOR("CONTRACTOR");

        private String value;

        ExecutingAuthorityEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static ExecutingAuthorityEnum fromValue(String text) {
            for (ExecutingAuthorityEnum b : ExecutingAuthorityEnum.values()) {
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

    /**
     * Type of the contract. This will decide the other attributes of the contract and how it will be processed down the line.
     */
    public enum ContractTypeEnum {
        WORK_ORDER("WORK_ORDER"),

        PURCHASE_ORDER("PURCHASE_ORDER");

        private String value;

        ContractTypeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static ContractTypeEnum fromValue(String text) {
            for (ContractTypeEnum b : ContractTypeEnum.values()) {
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


package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import digit.models.coremodels.AuditDetails;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity schema for letter of indent.
 */
@ApiModel(description = "Entity schema for letter of indent.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-08-04T15:05:28.525+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LetterOfIndent {
    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("letterOfIndentNumber")
    private String letterOfIndentNumber = null;

    @JsonProperty("workPackageNumber")
    private String workPackageNumber = null;

    @JsonProperty("workIdentificationNumber")
    private String workIdentificationNumber = null;

    @JsonProperty("fileNumber")
    private String fileNumber = null;

    @JsonProperty("fileDate")
    private BigDecimal fileDate = null;

    @JsonProperty("negotiatedPercentage")
    private BigDecimal negotiatedPercentage = null;

    @JsonProperty("agreementDate")
    private BigDecimal agreementDate = null;

    @JsonProperty("contractorId")
    private String contractorId = null;

    @JsonProperty("securityDeposit")
    private BigDecimal securityDeposit = null;

    @JsonProperty("bankGuarantee")
    private String bankGuarantee = null;

    @JsonProperty("emdAmount")
    private BigDecimal emdAmount = null;

    @JsonProperty("contractPeriod")
    private BigDecimal contractPeriod = null;

    @JsonProperty("defectLiabilityPeriod")
    private BigDecimal defectLiabilityPeriod = null;

    @JsonProperty("oicId")
    private UUID oicId = null;

    @JsonProperty("status")
    private StatusEnum status = null;

    @JsonProperty("letterStatus")
    private String letterStatus = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("bankGaurante")
    private Object bankGaurante = null;

    /**
     * It stores the status of the letter.
     */
    public enum StatusEnum {
        DRAFT("DRAFT"),

        ACTIVE("ACTIVE"),

        INACTIVE("INACTIVE");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
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


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
import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * Statement
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statement {
    @JsonProperty("id")

    @Valid
    private String id = null;

    @JsonProperty("tenantId")

    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("targetId")

    @Valid
    private String targetId = null;

    /**
     * Gets or Sets statementType
     */
    public enum StatementTypeEnum {
        ANALYSIS("ANALYSIS"),

        UTILIZATION("UTILIZATION");

        private String value;

        StatementTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StatementTypeEnum fromValue(String text) {
            for (StatementTypeEnum b : StatementTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("statementType")

    private StatementTypeEnum statementType = null;

    @JsonProperty("basicSorDetails")
    @Valid
    private List<BasicSorDetails> basicSorDetails = null;

    @JsonProperty("sorDetails")
    @Valid
    private List<SorDetail> sorDetails = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


    public Statement addAmountDetailsItem(BasicSorDetails basicSorDetailsItem) {
        if (this.basicSorDetails == null) {
            this.basicSorDetails = new ArrayList<>();
        }
        this.basicSorDetails.add(basicSorDetailsItem);
        return this;
    }

    public Statement addSorDetailsItem(SorDetail sorDetailsItem) {
        if (this.sorDetails == null) {
            this.sorDetails = new ArrayList<>();
        }
        this.sorDetails.add(sorDetailsItem);
        return this;
    }

}

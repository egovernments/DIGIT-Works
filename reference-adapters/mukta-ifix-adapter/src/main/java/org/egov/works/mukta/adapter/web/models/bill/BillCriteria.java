package org.egov.works.mukta.adapter.web.models.bill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * BillCriteria
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillCriteria {

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId;

    @JsonProperty("ids")
    private Set<String> ids;

    @JsonProperty("businessService")
    @Size(min = 2, max = 64)
    private String businessService;

    @JsonProperty("referenceIds")
    private Set<String> referenceIds;

    @JsonProperty("billNumbers")
    private Set<String> billNumbers;

    @JsonProperty("status")
    private String status;

    @JsonProperty("statusNot")
    private String statusNot;

    @JsonProperty("isPaymentStatusNull")
    private Boolean isPaymentStatusNull;

    public BillCriteria addIdsItem(String idsItem) {
        if (this.ids == null) {
            this.ids = new HashSet<>();
        }
        this.ids.add(idsItem);
        return this;
    }

    public BillCriteria addReferenceIdItem(String referenceIdItem) {
        if (this.referenceIds == null) {
            this.referenceIds = new HashSet<>();
        }
        this.referenceIds.add(referenceIdItem);
        return this;
    }

}

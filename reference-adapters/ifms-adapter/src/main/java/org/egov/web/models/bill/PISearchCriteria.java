package org.egov.web.models.bill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.data.query.annotations.Exclude;
import org.egov.web.models.jit.SanctionDetailsSearchCriteria;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PISearchCriteria {

    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("ward")
    private String ward;

    @JsonProperty("paymentInstructionType")
    private String paymentInstructionType;

    @JsonProperty("projectName")
    private String projectName;

    @JsonProperty("billNo")
    private String billNo;

    @JsonProperty("piStatus")
    private String piStatus;

    @JsonProperty("createdFrom")
    private Long createdFrom;

    @JsonProperty("createdTo")
    private Long createdTo;

    @JsonProperty("boundaryCode")
    private String boundaryCode;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("sortBy")
    private SanctionDetailsSearchCriteria.SortBy sortBy;

    @JsonProperty("sortOrder")
    private SanctionDetailsSearchCriteria.SortOrder sortOrder;
    @JsonIgnore
    private Boolean isCountNeeded = false;

    public PISearchCriteria addIdItem(String idItem) {
        if (this.ids == null) {
            this.ids = new ArrayList<>();
        }
        this.ids.add(idItem);
        return this;
    }
}

package org.egov.web.models.bill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PISearchCriteria {
    @JsonProperty("id")
    private List<String> id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;//mand

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("ward")
    private String ward = null;

    @JsonProperty("paymentInstructionType")
    private String paymentInstructionType = null;

    @JsonProperty("projectName")
    private String projectName = null;

    @JsonProperty("billNo")
    private String billNo = null;

    @JsonProperty("applicationNumber")
    private String applicationNumber = null;

    @JsonProperty("applicationStatus")
    private String applicationStatus = null;

    @JsonProperty("contactMobileNumber")
    private String contactMobileNumber = null;

    @JsonProperty("createdFrom")
    private Long createdFrom = null;

    @JsonProperty("createdTo")
    private Long createdTo = null;

    @JsonProperty("boundaryCode")
    private String boundaryCode = null;

    @JsonProperty("identifierType")
    private String identifierType = null;

    @JsonProperty("identifierValue")
    private String identifierValue = null;

    @JsonProperty("includeDeleted")
    private Boolean includeDeleted = false;

    @JsonIgnore
    private Boolean isCountNeeded = false;

    public PISearchCriteria addIdItem(String idItem) {
        if (this.id == null) {
            this.id = new ArrayList<>();
        }
        this.id.add(idItem);
        return this;
    }

}

package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.Document;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Organisation registry
 */
@ApiModel(description = "Organisation registry")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Organisation {

    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("applicationNumber")
    private String applicationNumber = null;//idgen formatted number from start of the org creation request

    @JsonProperty("orgNumber")
    @Size(min = 1, max = 64)
    private String orgNumber = null;//idgen formatted number once workflow is 'APPROVED'

    @JsonProperty("applicationStatus")
    @Size(min = 2, max = 64)
    private String applicationStatus = null;//workflow status

    @JsonProperty("externalRefNumber")
    @Size(min = 2, max = 64)
    private String externalRefNumber = null;

    @JsonProperty("dateOfIncorporation")
    private Double dateOfIncorporation = null;

    @JsonProperty("orgAddress")
    @Valid
    private List<Address> orgAddress = null;

    @JsonProperty("contactDetails")
    @Valid
    private List<ContactDetails> contactDetails = null;

    @JsonProperty("identifiers")
    @Valid
    @Size(min = 1)
    private List<Identifier> identifiers = null;

    @JsonProperty("functions")
    @Valid
    @Size(min = 1)
    private List<Function> functions = null;

    @JsonProperty("jurisdiction")
    private List<String> jurisdiction = null;

    @JsonProperty("isActive")
    private Boolean isActive = null;

    @JsonProperty("documents")
    @Valid
    private List<Document> documents = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    public Organisation addOrgAddressItem(Address orgAddressItem) {
        if (this.orgAddress == null) {
            this.orgAddress = new ArrayList<>();
        }
        this.orgAddress.add(orgAddressItem);
        return this;
    }

    public Organisation addContactDetailsItem(ContactDetails contactDetailsItem) {
        if (this.contactDetails == null) {
            this.contactDetails = new ArrayList<>();
        }
        this.contactDetails.add(contactDetailsItem);
        return this;
    }

    public Organisation addIdentifiersItem(Identifier identifiersItem) {
        if (this.identifiers == null) {
            this.identifiers = new ArrayList<>();
        }
        this.identifiers.add(identifiersItem);
        return this;
    }

    public Organisation addFunctionsItem(Function functionsItem) {
        if (this.functions == null) {
            this.functions = new ArrayList<>();
        }
        this.functions.add(functionsItem);
        return this;
    }

    public Organisation addJurisdictionItem(String jurisdictionItem) {
        if (this.jurisdiction == null) {
            this.jurisdiction = new ArrayList<>();
        }
        this.jurisdiction.add(jurisdictionItem);
        return this;
    }

    public Organisation addDocumentsItem(digit.models.coremodels.Document documentsItem) {
        if (this.documents == null) {
            this.documents = new ArrayList<>();
        }
        this.documents.add(documentsItem);
        return this;
    }

}

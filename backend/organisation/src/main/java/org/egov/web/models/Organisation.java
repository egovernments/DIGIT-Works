package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Organisation registry
 */
@ApiModel(description = "Organisation registry")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

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

    @JsonProperty("name")
    @NotNull
    @Size(min = 2, max = 128)
    private String name = null;

    @JsonProperty("applicationNumber")
    private String applicationNumber = null;//idgen formatted number from start of the org creation request

    //Decided on 14th March : As of now, orgnumber will be generated from start of create org registry
    @JsonProperty("orgNumber")
    private String orgNumber = null;//idgen formatted number once workflow is 'APPROVED'

    @JsonProperty("applicationStatus")
    private ApplicationStatus applicationStatus = null;

    @JsonProperty("externalRefNumber")
    @Size(min = 2, max = 64)
    private String externalRefNumber = null;

    @JsonProperty("dateOfIncorporation")
    private BigDecimal dateOfIncorporation = null;

    @JsonProperty("orgAddress")
    @Valid
    private List<Address> orgAddress = null;//no

    @JsonProperty("contactDetails")
    @Valid
    @NotNull
    @Size(min = 1)
    private List<ContactDetails> contactDetails = null;//no, as of now,it'll be only one

    @JsonProperty("identifiers")
    @Valid
    @Size(min = 1)
    private List<Identifier> identifiers = null;//upsert

    @JsonProperty("functions")
    @Valid
    @Size(min = 1)
    private List<Function> functions = null;//upsert

    @JsonProperty("jurisdiction")
    private List<Jurisdiction> jurisdiction = null;

    @JsonProperty("isActive")
    private Boolean isActive = null;

    @JsonProperty("documents")
    @Valid
    private List<Document> documents = null;//upsert

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

    public Organisation addJurisdictionItem(Jurisdiction jurisdictionItem) {
        if (this.jurisdiction == null) {
            this.jurisdiction = new ArrayList<>();
        }
        this.jurisdiction.add(jurisdictionItem);
        return this;
    }

    public Organisation addDocumentsItem(Document documentsItem) {
        if (this.documents == null) {
            this.documents = new ArrayList<>();
        }
        this.documents.add(documentsItem);
        return this;
    }

}

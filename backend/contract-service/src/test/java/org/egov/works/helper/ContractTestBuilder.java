package org.egov.works.helper;

import org.egov.works.web.models.Contract;
import org.egov.works.web.models.Status;

import java.math.BigDecimal;
import java.util.Collections;

public class ContractTestBuilder {
    private Contract.ContractBuilder builder;

    public ContractTestBuilder() {
        this.builder = Contract.builder();
    }

    public static ContractTestBuilder builder() {
        return new ContractTestBuilder();
    }

    public Contract build() {
        return this.builder.build();
    }

    public ContractTestBuilder withValidContract() {

        this.builder.id("some-id")
                .contractNumber("some-number")
                .tenantId("pb.amritsar")
                .wfStatus("some-wfStatus")
                .executingAuthority("some-executingAuthority")
//                .totalContractedamount(BigDecimal.valueOf(1234567890234567L))
                .agreementDate(BigDecimal.valueOf(2022))
                .defectLiabilityPeriod(BigDecimal.valueOf(123456787654321L))
                .orgId("some-orgId")
                .startDate(BigDecimal.valueOf(2022))
                .endDate(BigDecimal.valueOf(2023))
                .status(Status.ACTIVE)
                .lineItems(LineItemsTestBuilder.withLineItemsList())
                .documents(Collections.singletonList(DocumentTestBuilder.builder().addGoodDocument().build()))
                .auditDetails(null)
                .additionalDetails(new Object())
                .additionalDetails(AdditionalFields.builder().build())
                .contractType("some-contractType")
                .completionPeriod(2);
        return this;
    }

    public ContractTestBuilder contractWithOutStartingDateAgreementDateEndDate() {
        this.builder.id("some-id")
                .contractNumber("some-number")
                .tenantId("pb.amritsar")
                .wfStatus("some-wfStatus")
                .executingAuthority("some-executingAuthority")
//                .totalContractedamount(BigDecimal.valueOf(1234567890234567L))
                .defectLiabilityPeriod(BigDecimal.valueOf(123456787654321L))
                .orgId("some-orgId")
                .status(Status.ACTIVE)
                .lineItems(Collections.singletonList(LineItemsTestBuilder.builder().withLineItems().build()))
                .documents(Collections.singletonList(DocumentTestBuilder.builder().addGoodDocument().build()))
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build())
                .additionalDetails(new Object())
                .additionalDetails(AdditionalFields.builder().build())
                .contractType("some-contractType");
        return this;
    }
}

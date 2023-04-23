package org.egov.works.helper;

import org.egov.works.web.models.AmountBreakup;
import org.egov.works.web.models.Status;

import java.math.BigDecimal;

public class AmountBreakupTestBuilder {
    private AmountBreakup.AmountBreakupBuilder builder;

    public AmountBreakupTestBuilder() {
        this.builder = AmountBreakup.builder();
    }

    public AmountBreakup build() {
        return this.builder.build();
    }

    public static AmountBreakupTestBuilder builder() {
        return new AmountBreakupTestBuilder();
    }

    public AmountBreakupTestBuilder withAmountBreakup() {
        this.builder.id("ID-1")
                .estimateAmountBreakupId("some-estimateId")
                .amount((double) 123465432L)
                .status(Status.ACTIVE)
                .lineItemId("some-lineItemId")
                .additionalDetails(new Object())
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build())
                .additionalDetails(AdditionalFields.builder().build());
        return this;
    }


}

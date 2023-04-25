package org.egov.works.helper;

import org.egov.works.web.models.LineItems;
import org.egov.works.web.models.Status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineItemsTestBuilder {
    private LineItems.LineItemsBuilder builder;

    public LineItemsTestBuilder() {
        this.builder = LineItems.builder();
    }

    public LineItems build() {
        return this.builder.build();
    }

    public static LineItemsTestBuilder builder() {
        return new LineItemsTestBuilder();
    }

    public LineItemsTestBuilder withLineItems() {
        this.builder.id("ID-1")
                .estimateId("some-estimateId")
                .estimateLineItemId("some-estimateId")
                .tenantId("pb.amritsar")
                .unitRate(Double.valueOf(12345654321L))
                .noOfunit(Double.valueOf((123432123L)))
                .status(Status.ACTIVE)
                .amountBreakups(Collections.singletonList(AmountBreakupTestBuilder.builder().withAmountBreakup().build()))
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build())
                .additionalDetails(new Object())
                .contractId("some-contractId")
                .additionalDetails(AdditionalFields.builder().build());
        return this;
    }

    public static List<LineItems> withLineItemsList() {
        List<LineItems> lineItemsTestBuilders = new ArrayList<>();
        LineItems lineItems = LineItems.builder().id("ID-1")
                .estimateId("some-estimateId")
                .estimateLineItemId("some-estimateId")
                .tenantId("pb.amritsar")
                .unitRate(Double.valueOf((12345654321L)))
                .noOfunit(Double.valueOf((123432123L)))
                .status(Status.ACTIVE)
                .amountBreakups(Collections.singletonList(AmountBreakupTestBuilder.builder().withAmountBreakup().build()))
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build())
                .additionalDetails(new Object())
                .contractId("some-contractId")
                .additionalDetails(AdditionalFields.builder().build())
                .build();
        lineItemsTestBuilders.add(lineItems);

        return lineItemsTestBuilders;
    }
}

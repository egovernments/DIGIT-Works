package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.works.services.common.models.expense.Party;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalcDetail {
	@JsonProperty("id")

	@Valid
	private UUID id = null;

	@JsonProperty("billId")

	@Size(min = 2, max = 64)
	private String billId = null;

	@JsonProperty("referenceId")

	@Size(min = 2, max = 64)
	private String referenceId = null;

	@JsonProperty("fromPeriod")

	@Valid
	private BigDecimal fromPeriod = null;

	@JsonProperty("toPeriod")

	@Valid
	private BigDecimal toPeriod = null;

	@JsonProperty("payee")

	@Valid
	private Party payee = null;

	@JsonProperty("lineItems")
	@Valid
	private List<LineItem> lineItems = null;

	@JsonProperty("payableLineItem")
	@NotNull
	@Valid
	private List<LineItem> payableLineItem = new ArrayList<>();

	public CalcDetail addLineItemsItem(LineItem lineItemsItem) {
		if (this.lineItems == null) {
			this.lineItems = new ArrayList<>();
		}
		this.lineItems.add(lineItemsItem);
		return this;
	}

	public CalcDetail addPayableLineItemItem(LineItem payableLineItemItem) {
		this.payableLineItem.add(payableLineItemItem);
		return this;
	}

}

package org.egov.digit.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Object which holds the info about the expense details
 */
@Schema(description = "A Object which holds the info about the expense details")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-15T12:39:54.253+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Demand {
	@JsonProperty("id")

	@Valid
	private UUID id = null;

	@JsonProperty("tenantId")
	@NotNull

	@Size(min = 2, max = 64)
	private String tenantId = null;

	/**
	 * Demand type enum
	 */
	public enum DemandTypeEnum {
		EXPENSE("EXPENSE");

		private String value;

		DemandTypeEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static DemandTypeEnum fromValue(String text) {
			for (DemandTypeEnum b : DemandTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("demandType")
	@NotNull

	private DemandTypeEnum demandType = null;

	@JsonProperty("businessService")
	@NotNull

	@Size(min = 2, max = 64)
	private String businessService = null;

	@JsonProperty("referenceId")

	@Size(min = 2, max = 64)
	private String referenceId = null;

	@JsonProperty("fromPeriod")

	@Valid
	private BigDecimal fromPeriod = null;

	@JsonProperty("toPeriod")

	@Valid
	private BigDecimal toPeriod = null;

	@JsonProperty("billGenDate")

	@Valid
	private BigDecimal billGenDate = null;

	@JsonProperty("payee")

	@Valid
	private Party payee = null;

	@JsonProperty("payer")

	@Valid
	private Party payer = null;

	@JsonProperty("lineItems")
	@NotNull
	@Valid
	private List<LineItems> lineItems = new ArrayList<>();

	@JsonProperty("additionalFields")

	private Object additionalFields = null;

	@JsonProperty("auditDetails")

	@Valid
	private AuditDetails auditDetails = null;

	public Demand addLineItemsItem(LineItems lineItemsItem) {
		this.lineItems.add(lineItemsItem);
		return this;
	}

}

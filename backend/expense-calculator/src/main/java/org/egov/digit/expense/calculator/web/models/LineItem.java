package org.egov.digit.expense.calculator.web.models;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.egov.common.contract.models.AuditDetails;
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
 * Line items are the amount breakups for net amounts
 */
@Schema(description = "Line items are the amount breakups for net amounts")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LineItem {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("billDetailId")
	@Valid
	private String billDetailId;

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId = null;

	@JsonProperty("headCode")
	@NotNull

	@Size(min = 2, max = 64)
	private String headCode = null;

	@JsonProperty("amount")
	@NotNull
	@Valid
	private BigDecimal amount = null;

	/**
	 * Type of line item
	 */
	public enum TypeEnum {
		PAYABLE("PAYABLE"),

		DEDUCTION("DEDUCTION");

		private String value;

		TypeEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static TypeEnum fromValue(String text) {
			for (TypeEnum b : TypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("type")
	@NotNull
	private TypeEnum type = null;

	@JsonProperty("paidAmount")
	@Valid
	private BigDecimal paidAmount = null;

	@JsonProperty("status")
	private String status = null;

	@JsonProperty("isLineItemPayable")
	private Boolean isLineItemPayable;

	@JsonProperty("additionalDetails")
	private Object additionalDetails = null;

	@JsonProperty("auditDetails")
	@Valid
	private AuditDetails auditDetails = null;

}

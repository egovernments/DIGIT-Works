package org.egov.digit.expense.calculator.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Criteria
 *
 * ================================================================================
 * PURPOSE & BUSINESS CONTEXT
 * ================================================================================
 *
 * Request criteria for expense calculation / bill generation.
 * Used in /v1/_calculate API to specify what to calculate bills for.
 *
 * V1 USAGE (Legacy - Single Bill):
 * ---------------------------------
 * - tenantId + referenceId + localityCode + musterRollId(s)
 * - Calculates bill for specific muster rolls
 * - No billingPeriodId (null)
 *
 * V2 USAGE (Intermediate Billing - Per-Period Bills):
 * ---------------------------------------------------
 * - tenantId + referenceId + localityCode + billingPeriodId
 * - billingPeriodId specifies which billing period to calculate
 * - System searches for muster rolls matching the period
 * - Special value: billingPeriodId = "AGGREGATE" triggers aggregate bill
 *
 * FIELD DESCRIPTIONS:
 * -------------------
 * - tenantId: ULB/State identifier (required)
 * - referenceId: Campaign/Project number (required)
 * - localityCode: Boundary locality code (required)
 * - musterRollId: List of muster roll IDs (V1 - explicit list)
 * - billingPeriodId: Period to bill (V2 - system finds muster rolls)
 * - fromPeriod/toPeriod: Date range filters (epoch milliseconds)
 * - auditDetails: Standard DIGIT audit trail
 *
 * V2 DETECTION:
 * -------------
 * billingPeriodId != null AND billingPeriodId != "AGGREGATE" → V2 flow
 * billingPeriodId == null OR musterRollId provided → V1 flow
 *
 * ================================================================================
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Criteria {
	@JsonProperty("tenantId")
	@NotNull

	private String tenantId = null;

	@JsonProperty("musterRollId")

	private List<String> musterRollId = null;

	@JsonProperty("fromPeriod")

	@Valid
	private BigDecimal fromPeriod = null;

	@JsonProperty("toPeriod")

	@Valid
	private BigDecimal toPeriod = null;

	@JsonProperty("referenceId")
	@NotNull
	private String referenceId = null;

	@JsonProperty("localityCode")
	@NotNull
	private String localityCode = null;

	@JsonProperty("hierarchyType")
	private String hierarchyType = null;

	@JsonProperty("billingPeriodId")
	private String billingPeriodId = null;

	@JsonProperty("auditDetails")

	@Valid
	private AuditDetails auditDetails = null;

	public Criteria addMusterRollIdItem(String musterRollIdItem) {
		if (this.musterRollId == null) {
			this.musterRollId = new ArrayList<>();
		}
		this.musterRollId.add(musterRollIdItem);
		return this;
	}

}

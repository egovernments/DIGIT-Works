package org.egov.digit.expense.web.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill {
	@JsonProperty("id")
	@Valid
	private UUID id;

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;

	@JsonProperty("billDate")
	@Valid
	private BigDecimal billDate;

	@JsonProperty("dueDate")
	@Valid
	private BigDecimal dueDate;

	@JsonProperty("netPayableAmount")
	@Valid
	private BigDecimal netPayableAmount;

	@JsonProperty("netPaidAmount")
	@Valid
	private BigDecimal netPaidAmount;

	@JsonProperty("businessService")
	@NotNull
	@Size(min = 2, max = 64)
	private String businessService;

	@JsonProperty("referenceId")
	@Size(min = 2, max = 64)
	private String referenceId;

	@JsonProperty("fromPeriod")
	@Valid
	private BigDecimal fromPeriod;

	@JsonProperty("toPeriod")
	@Valid
	private BigDecimal toPeriod;
	
	@JsonProperty("paymentStatus")
	@Size(min = 2, max = 64)
	private String paymentStatus;

	@JsonProperty("wfStatus")
	@Size(min = 2, max = 64)
	private String wfStatus;

	@JsonProperty("payer")
	@NotNull
	@Valid
	private Party payer;

	@JsonProperty("billDetails")
	@NotNull
	@Valid
	private List<BillDetail> billDetails;

	@JsonProperty("additionalFields")
	private Object additionalFields;

	@JsonProperty("auditDetails")
	@Valid
	private AuditDetails auditDetails;

	public Bill addBillDetailsItem(BillDetail billDetailsItem) {
		this.billDetails.add(billDetailsItem);
		return this;
	}

}
